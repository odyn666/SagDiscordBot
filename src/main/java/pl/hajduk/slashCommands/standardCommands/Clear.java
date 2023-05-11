package pl.hajduk.slashCommands.standardCommands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.hajduk.config.Check;
import pl.hajduk.config.FromJson;

import java.util.ArrayList;
import java.util.List;

public class Clear extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //User user=event.getMessage().getAuthor();
        Member member = event.getMember()   ;
        FromJson fj = new FromJson();
        String[] args = event.getMessage().getContentRaw().substring(0).split(" ");
        Check check = new Check();
        //TODO add slash command for selecting role id for operating commands
        if (member != null && args[0].equals(fj.getPrefix().toString() + "clear") && (check.hasPermission(member, Permission.MANAGE_CHANNEL) || check.hasRole(member, "869528487944413194")) || member.getId().equals("227769412059529216")) {

            System.out.println(args[1]);


            List<Message> messageList = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
            TextChannel channel = event.getChannel().asTextChannel();
            if (Integer.parseInt(args[1]) > 2)
                channel.deleteMessages(messageList).queue();
            event.getJDA().openPrivateChannelById(event.getGuild().getOwnerId()).queue((privateChannel) -> channel.sendMessage("ammount must be at lest 2"));


        } else if (member == null) {
            System.out.println("member: " + member);
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


}
    
