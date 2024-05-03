package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.util.List;

public class Clear implements ICommand {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear queue";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING,getName(),getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        PlayerManager playerManager=PlayerManager.get();
        if (!playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getQueue().isEmpty()){
            playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().clearQueue();
        }else {
            event.reply("QUEUE IS EMPTY").queue();
        }

    }
}
