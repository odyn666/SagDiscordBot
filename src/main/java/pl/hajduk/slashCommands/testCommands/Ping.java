package pl.hajduk.slashCommands.testCommands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter
    {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
        {
       String userMessage=event.getMessage().getContentRaw();
       if (userMessage.equalsIgnoreCase("ping"))
           {
           event.getChannel().sendMessage("pong").queue();
           for (int i = 0; i < 30; i++)
               {
               event.getJDA().getGatewayPing();
               }
           }
       
       else if (userMessage.equals("komar"))
           {
           event.getChannel().sendMessage("GrAMYwGrOuNdBranChA?").queue();
           }
        }
    
    }
