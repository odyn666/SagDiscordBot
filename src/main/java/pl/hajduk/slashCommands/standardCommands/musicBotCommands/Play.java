package pl.hajduk.slashCommands.standardCommands.musicBotCommands;


import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import pl.hajduk.service.audio.musicBot.PlayerManager;

import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Play implements ICommand {
    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Will play a song";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "name", "Name of the song to play", true));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
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

        String url = event.getOption("name").getAsString();
        try {
            URI uri = new URI(url);
        } catch (URISyntaxException e) {
            url = "ytsearch:" + url;
            PlayerManager.get().play(event.getGuild(), url);
        }

        PlayerManager.get().play(event.getGuild(), url);
        event.reply("Playing").queue();
//        playerManager.play(event.getGuild(), url);
        System.out.println(url);
        // AudioReceiver receiver=new AudioReceiver();


    }
}

