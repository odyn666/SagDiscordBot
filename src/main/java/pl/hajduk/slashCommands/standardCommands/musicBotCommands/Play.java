package pl.hajduk.slashCommands.standardCommands.musicBotCommands;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Play implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(Play.class);

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

    @SneakyThrows
    @Override
    public void execute(SlashCommandInteractionEvent event) {

        event.deferReply().queue();
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        PlayerManager playerManager = PlayerManager.get();
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
        //! this must leave here until i figure out how to wait till music finishes downloading
        Thread.sleep(10_000);

        AudioTrack playingTrack = playerManager.getGuildMusicManager(event.getGuild()).getPlayer().getPlayingTrack();

        String songName = Paths.get(playingTrack.getInfo().uri).getFileName().toString();
        //  event.getHook().sendMessage("Playing: " + Paths.get(playingTrack.getInfo().uri).getFileName().toString()).queue();
        event.getHook().sendMessage("Playing: " + songName).queue();
        log.info("URL: " + url);

    }

}

