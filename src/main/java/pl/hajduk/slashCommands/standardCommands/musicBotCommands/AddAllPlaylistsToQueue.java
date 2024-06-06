package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.io.File;
import java.util.List;

public class AddAllPlaylistsToQueue implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(AddAllPlaylistsToQueue.class);

    @Override
    public String getName() {
        return "addall";
    }

    @Override
    public String getDescription() {
        return "add all donwloaded playlists to queue";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, getName(), getDescription()));
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

        List<File> localTracks = PlayerManager.get().getAllLocalTracks();
        if (localTracks.isEmpty()) {
            log.info("LOCAL TRACKS IS EMPTY");
        } else {
            localTracks.forEach(System.out::println);
        }
        localTracks.forEach(track -> {
            PlayerManager.get().play(event.getGuild(), track.getAbsolutePath());
            log.info(track.getAbsolutePath());
        });


    }
}
