package pl.hajduk.Listeners;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Getter
@Setter
public class TwatListener extends ListenerAdapter implements Runnable {
    private boolean spammingON;

    @lombok.SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        int i = 0;

        String id = event.getMember().getUser().getId();
        String text = "look what you made me to do <@" + id + ">";
        boolean fck = message.contains("<@227769412059529216>") || message.contains("@Inspektor Odyński");
        if (fck) {
            while (true) {
                message = event.getMessage().getContentRaw();
                if (message.contains("chujów sto")) break;
                if (message.contains("@Inspektor Odyński")) {
                    text += "<@" + id + ">" + "fuck you too";
                }
                event.getMember().getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("<@"+ event.getMember().getId() + ">>" + " HANS ARE WE THE BADDIES?")).queue();
                Thread.sleep(500);
                event.getChannel().sendMessage(text).queue();
//                Thread.sleep(1000);
                i++;

                if (i >= 200) break;


            }
        }

        if (message.contains("chuje")) {
            while (!message.contains("chuje stop")) {
                event.getChannel().sendMessage("look what you made me to do <@600342342498779136> <@810959719656456192>  <@306814505302884354>  ").queue();
                Thread.sleep(500);
            }
        }
    }

    @Override
    public void run() {

    }
}
