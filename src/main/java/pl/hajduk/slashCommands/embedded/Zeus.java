package pl.hajduk.slashCommands.embedded;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;


import java.util.*;


public class Zeus extends ListenerAdapter
    {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
        {
        Category category = null;
        if (event.getName().equals("zeus"))
            {

            //TODO add imges to briefing

           
            TextInput missionName = TextInput.create("missionName", "Mission Name", TextInputStyle.SHORT)
                    .setPlaceholder("Mission name")
                    .setMinLength(3)
                    .setMaxLength(100) // or setRequiredRange(10, 100)
                    .setRequired(true)
                    .build();

            TextInput image=TextInput.create("image","image",TextInputStyle.SHORT)
                    .setPlaceholder("image baner")
                    .setRequired(false)
                    .build();
            
            TextInput description = TextInput.create("description", "Brief", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Detailed info about mission")
                    .setMinLength(30)
                    .setMaxLength(1000)
                    .setRequired(true)
                    .build();
            
            TextInput date = TextInput.create("date", "Mission date", TextInputStyle.SHORT)
                    .setPlaceholder("Mission date should be GMT+1")
                    .setRequired(true)
                    .setMaxLength(200)
                    .build();
            
            TextInput missionType = TextInput.create("missionType", "mission Type", TextInputStyle.SHORT)
                    .setPlaceholder("eg. Mil-sim,casual")
                    .setRequired(true)
                    .setMaxLength(200).build();
            
            Modal modal = Modal.create("zeusModal", "Zeus")
                    .addComponents(ActionRow.of(image),ActionRow.of(missionName), ActionRow.of(date), ActionRow.of(missionType), ActionRow.of(description))
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


