package pl.hajduk.service.audio.musicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;

import java.nio.Buffer;
import java.nio.ByteBuffer;


public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer player;
    private final MutableAudioFrame frame = new MutableAudioFrame();
    ByteBuffer buffer;
    private AudioFrame audioFrame;
    private int time;

    public AudioPlayerSendHandler(AudioPlayer player) {
        this.player = player;

        this.buffer = ByteBuffer.allocate(1024);
        frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        //TODO check if commentd lines work
//        audioFrame=player.provide();
//        boolean canProvide = player.provide(frame);
//        if(!canProvide) {
//            time += 20;
//            if(time >= 300000) {
//                time = 0;
//                guild.getAudioManager().closeAudioConnection();
//            }
//        } else {
//            time = 0;
//        }
//        return canProvide;
        return this.player.provide(this.frame);
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        final Buffer buffer = ((Buffer) this.buffer).flip();
        return (ByteBuffer) buffer;
    }

    @Override
    public boolean isOpus() {
        return true;
    }

    public void clearQueue() {

    }
}
