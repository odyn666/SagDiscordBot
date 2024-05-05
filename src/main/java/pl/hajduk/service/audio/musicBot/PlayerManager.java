package pl.hajduk.service.audio.musicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.util.*;

@Log
public class PlayerManager {
    private static PlayerManager INSTANCE;
    private Map<Long, GuildMusicManager> guildMusicManagers = new HashMap<>();
    private AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

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
                trackPaths.forEach(track -> loadTrackToQueue(track.getPath(),guildMusicManager));
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


    int playlistNo = 1;

    public List<File> downloadMusicFromYT(String ytVid) {
        String dirPath = "/home/odyn/music/pl" + playlistNo + "/";
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
        return result;
    }
}
