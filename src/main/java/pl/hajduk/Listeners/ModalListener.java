package pl.hajduk.Listeners;


import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModalListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {


        if (event.getModalId().equals("zeusModal")) {
            String missionName = event.getValue("missionName").getAsString();
            String description = event.getValue("description").getAsString();
            String date = event.getValue("date").getAsString();
            String missionType = event.getValue("missionType").getAsString();
            String image = event.getValue("image").getAsString();

            String[] dateForChannelName = date.split(" ");

            Pattern dateRegex = Pattern.compile("(\\d{2})[./-](\\d{2})[./-](\\d{4})");
            String dateString = dateForChannelName[0];
            Matcher matcher = dateRegex.matcher(dateString);
            String dateYear = matcher.replaceAll("$1 $2 $3");

            Category category = event.getGuild().getCategoryById(1020356427484758116L);


            //Optional<Member> memberOptional = event.getGuild().getMembersByName(missionName, true).stream().findFirst();

            category.createTextChannel(dateYear + "-" + missionName).addPermissionOverride(event.getGuild().getRoleById(1015734611726303323L), EnumSet.of(Permission.VIEW_CHANNEL), null).queue(e -> {
                String channelID = e.getId().toString();
                TextChannel newChannel = event.getJDA().getTextChannelById(channelID);
                if (newChannel != null) {
                    if (!image.isEmpty()) {
                        newChannel.sendMessage(image).queue();
                    }

                    newChannel.sendMessage("```Mission Name: ```" + missionName + "\n"
                            + "```missionType/Rodzaj misji: ```" + missionType + "\n" +
                            "```Date/Data: ```" + date + "\n" +
                            description).queue();
                }

            });
            event.reply("Channel was succesfully created.You can close this message\n " +
                    "Your mission channel was successfully created. Next thing you need to do is to add information about slots and monitor activities on this channel.\n"
                    + "Kanał został pomyślnie utworzony.Możesz zamknąć tą wiadomość\n"
                    + "twój kanał misji został stworzony pomyślnie. to co musisz teraz zrobić to zamieścić informacje o slotach i obserwować aktywność na kanale\n").setEphemeral(true).queue();


        }


    }
}
