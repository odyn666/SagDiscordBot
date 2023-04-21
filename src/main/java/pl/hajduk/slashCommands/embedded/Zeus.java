package pl.hajduk.slashCommands.embedded;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;


import java.util.*;


public class Test extends ListenerAdapter
    {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
        {
        Category category = null;
        if (event.getName().equals("zeus"))
            {

            
           
            TextInput missionName = TextInput.create("missionName", "Mission Name", TextInputStyle.SHORT)
                    .setPlaceholder("Mission name")
                    .setMinLength(10)
                    .setMaxLength(100) // or setRequiredRange(10, 100)
                    .build();
            
            TextInput description = TextInput.create("description", "description", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Your concerns go here")
                    .setMinLength(30)
                    .setMaxLength(1000)
                    .build();
            
            TextInput date = TextInput.create("date", "Mission date", TextInputStyle.SHORT)
                    .setPlaceholder("Mission date shoud be GMT+1")
                    .setMaxLength(200).build();
            
            TextInput missionType = TextInput.create("missionType", "mission Type", TextInputStyle.SHORT)
                    .setPlaceholder("eg. Mil-sim,casual")
                    .setMaxLength(200).build();
            
            Modal modal = Modal.create("zeusModal", "Zeus")
                    .addComponents(ActionRow.of(missionName), ActionRow.of(date), ActionRow.of(missionType), ActionRow.of(description))
                    .build();
            
            
            event.replyModal(modal).queue();
            
            }
        }
    
    public void createTextChannel(Member member, String channelName)
        {
        Guild guild = member.getGuild();
        
        guild.createTextChannel(channelName)
                
                .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                
                
                
                .queue();
        }
    
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event)
        {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("zeus", "Create channel for ZGM "));
        event.getGuild().updateCommands().addCommands(commandData).queue();
        }
    
    @Override
    public void onGuildJoin(GuildJoinEvent event)
        {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("zeus", "Create channel for ZGM "));
        event.getGuild().updateCommands().addCommands(commandData).queue();
        }
    }


