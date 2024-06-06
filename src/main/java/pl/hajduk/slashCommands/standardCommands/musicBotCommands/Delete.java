package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.io.File;
import java.util.List;

public class Delete implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(Delete.class);

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete playlists";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, getName(), getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String[] command = {"bash", "-c", "rm -r /home/sagbot/music/*"};

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO();
            Process process = pb.start();

            int exitCode = process.waitFor();
            log.info("File deletion status " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File("/home/odyn/music");
        if (!file.exists()) event.reply("error has occured fix code plz");


        event.reply("Playlists successful deleted");

    }
}
