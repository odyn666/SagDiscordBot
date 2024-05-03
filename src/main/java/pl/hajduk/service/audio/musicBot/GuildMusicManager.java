package pl.hajduk.service.audio.musicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;

@Getter
@Setter
public class GuildMusicManager {
    private final TrackScheduler trackScheduler;
    private final AudioPlayerSendHandler sendHandler;
    private final AudioPlayer player;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild) {
       this.player = manager.createPlayer();
       this.trackScheduler = new TrackScheduler(this.player);
       this.player.addListener(this.trackScheduler);
       this.sendHandler = new AudioPlayerSendHandler(this.player);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return this.sendHandler;
    }
}
