package pl.hajduk.slashCommands.standardCommands.sagCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.util.List;

public class Talk implements ICommand {
    @Override
    public String getName() {
        return "Talk";
    }

    @Override
    public String getDescription() {
        return "Talk with me";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING,getName(),getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {




    }
}
