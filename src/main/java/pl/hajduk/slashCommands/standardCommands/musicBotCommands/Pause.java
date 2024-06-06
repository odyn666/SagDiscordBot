package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.nio.file.Paths;
import java.util.List;

public class Pause implements ICommand {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "stop hammer time";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING,getName(),getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        PlayerManager playerManager=PlayerManager.get();
        AudioTrack playingTrack = playerManager.getGuildMusicManager(event.getGuild()).getPlayer().getPlayingTrack();
        playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getPlayer().setPaused(true);

        String songName = Paths.get(playingTrack.getInfo().uri).getFileName().toString();
        event.reply("Track Paused: "+songName).queue();
    }
}
