package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.nio.file.Paths;
import java.util.List;

public class Resume implements ICommand {
    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getDescription() {
        return "resume current song";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, getName(), getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        PlayerManager playerManager = PlayerManager.get();
        AudioTrack playingTrack = playerManager.getGuildMusicManager(event.getGuild()).getPlayer().getPlayingTrack();

        String songName = Paths.get(playingTrack.getInfo().uri).getFileName().toString();
        playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getPlayer().setPaused(false);
        event.reply("resumed track : " + songName).queue();
    }
}
