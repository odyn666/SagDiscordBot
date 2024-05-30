package pl.hajduk.service.audio.musicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.stream.Collectors;

@Log
public class PlayerManager {
    private static PlayerManager INSTANCE;
    private Map<Long, GuildMusicManager> guildMusicManagers = new HashMap<>();
    private AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    @Setter
    @Getter
    private List<File> localTracks;

    private PlayerManager() {
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    }

    public static PlayerManager get() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
        return guildMusicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            GuildMusicManager musicManager = new GuildMusicManager(audioPlayerManager, guild);

            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

            return musicManager;
        });
    }

    public void play(Guild guild, String trackURL) {
        GuildMusicManager guildMusicManager = getGuildMusicManager(guild);
        boolean isYoutubeSong = trackURL.toLowerCase().contains("youtube") || trackURL.toLowerCase().contains("youtu.be");
        if (isYoutubeSong) {
            List<File> trackPaths = downloadMusicFromYT(trackURL);
            if (trackPaths.size() > 1) {
                trackPaths.forEach(track -> loadTrackToQueue(track.getPath(), guildMusicManager));
            } else {
                trackURL = trackPaths.get(0).getPath();
                loadTrackToQueue(trackURL, guildMusicManager);
            }
        }

        if (!isYoutubeSong)
            loadTrackToQueue(trackURL, guildMusicManager);

    }

    private void loadTrackToQueue(String trackURL, GuildMusicManager guildMusicManager) {
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {

                guildMusicManager.getTrackScheduler().queue(track);

            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();
                if (!tracks.isEmpty()) {
                    guildMusicManager.getTrackScheduler().queue(tracks.get(0));

                }
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

    String plPath = "/home/sagbot/music/";
    int playlistNo = countDirPlaylist(plPath);


    public List<File> downloadMusicFromYT(String ytVid) {



        String dirPath = "/home/sagbot/music/pl" + playlistNo + "/";
        List<File> result;
        String command = "./yt-dlp -x --audio-format mp3 --output '" + dirPath + "%(title)s.%(ext)s' " + ytVid;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command + " --verbose");
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            log.info("YT-DLP EXITED WITH STATUS CODE" + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File directory = new File(dirPath);
        File[] files = directory.listFiles();
        result = Arrays.asList(files);
        ++playlistNo;
        setLocalTracks(result);
        return result;
    }

    private int countDirPlaylist(String dirPath) {
        File file = new File(dirPath);

        if (!file.exists()) {
            System.err.println("Directory does not exist: " + file.getAbsolutePath());
            file.mkdir();
        }
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        });
        System.out.println("ilość plików w folderze " + files.length);
        return files.length;

    }

    public File getCurrentLocalTrack(String uri){

       return this.localTracks.stream().filter(track-> track.getPath().equals(uri)).toList().get(0);
    }


    public List<File> getAllLocalTracks() {
        List<File> localTracks = new ArrayList<>();
        String dirPath = "/home/sagbot/music/pl";

        for (int i = 0; i <= playlistNo; i++) {
            String path = dirPath + i + "/";
            File directory = new File(path);

            if (!directory.exists()) {
                System.err.println("Directory does not exist: " + path);
                directory.mkdir();
                continue;
            }

            if (!directory.isDirectory()) {
                System.err.println("Not a directory: " + path);
                continue;
            }

            File[] files = directory.listFiles();

            if (files == null) {
                System.err.println("Failed to list files in directory: " + path);
                continue;
            }

            localTracks.addAll(Arrays.asList(files));
        }

        localTracks.forEach(System.out::println);
        return localTracks;
    }

}
