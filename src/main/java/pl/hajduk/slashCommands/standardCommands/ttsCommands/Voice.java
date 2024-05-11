package pl.hajduk.slashCommands.standardCommands.ttsCommands;

import com.google.cloud.speech.v1.*;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ManagedChannelProvider;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.audio.SpeakingMode;
import net.dv8tion.jda.api.audio.UserAudio;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

@Log
public class Voice implements ICommand, AudioReceiveHandler {

    @Override
    public String getName() {
        return "voice";
    }

    @Override
    public String getDescription() {
        return "come and talk with me";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, getName(), getDescription()));
    }

    @Override
    public void handleCombinedAudio(@NotNull CombinedAudio combinedAudio) {
        combinedAudio.getAudioData(1);

        // Send the audio data to a private channel
        User user = combinedAudio.getUsers().get(0);
        user.openPrivateChannel().flatMap(c -> c.sendMessage("You said: " + "getAudioDataAsString(combinedAudio.getAudioData(1)))")).queue();
    }

    private String getAudioDataAsString(byte[] audioData) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AudioFormat audioFormat = new AudioFormat(PCM_SIGNED, 16000, 16, 2, 4 * audioData.length,50.0f,false);
//                AudioSystem.write(audioData, AudioFileFormat.Type.WAVE, byteArrayOutputStream.writeTo(););
        byte[] audioBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(audioBytes);
    }

    @Override
    public void handleUserAudio(@NotNull UserAudio userAudio) {
        AudioReceiveHandler.super.handleUserAudio(userAudio);
        userAudio.getUser().openPrivateChannel().flatMap(c -> c.sendMessage("asd")).queue();
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        includeUserInCombinedAudio(event.getUser());
        if (!memberVoiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel").queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());

        } else {
            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("Im playing music on other channel, join my channel to queue music.\n" +
                        " disclaimer in order to play music on multiple channels send donation to grimm for server upgrade \n" +
                        " current workload on VPS is to high to host multiple instances of ArmA server and multiple instances of music bot  ").queue();
                return;
            }
        }

        // Listen to the audio data and send it to a private channel
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.setSelfMuted(false);

    }


}