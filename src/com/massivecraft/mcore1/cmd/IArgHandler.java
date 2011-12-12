package com.massivecraft.mcore1.cmd;

import org.bukkit.command.CommandSender;

import com.massivecraft.mcore1.plugin.MPlugin;

public interface IArgHandler<T>
{
	// Parse result returned - or null and something in the error message.
	public T parse(String str, String style, CommandSender sender, MPlugin p);
	
	// Error here - or null.
	public String error();
}
