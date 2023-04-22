package pl.hajduk.slashCommands.standardCommands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Clear extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {



        if (event.getName().equals("clear")) {
            String[] args = event.getName().split(" ");
            List<Message>messageList= event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])+1).complete();
            TextChannel channel= event.getChannel().asTextChannel();
            channel.deleteMessages(messageList).queue();



            if (args.length != 2)
                return;

            if (!isNumber(args[1]))
                event.reply("usage /clear <ammount>").queue();

        }

    }

    private boolean isNumber(String arg) {
        try {
            Integer.parseInt(arg);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("clear", "clear {ammount}"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("clear", "clear {ammount}"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
    
