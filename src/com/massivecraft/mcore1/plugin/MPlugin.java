package com.massivecraft.mcore1.plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.mcore1.MCore;
import com.massivecraft.mcore1.cmd.Cmd;
import com.massivecraft.mcore1.perm.Perm;
import com.massivecraft.mcore1.persist.Persist;
import com.massivecraft.mcore1.text.Txt;


public abstract class MPlugin extends JavaPlugin
{
	// Tools
	public Cmd cmd;
	public Persist persist;
	public Txt txt;
	public Perm perm;
	
	// -------------------------------------------- //
	// ENABLE
	// -------------------------------------------- //
	
	private long timeEnableStart;
	public boolean preEnable()
	{
		timeEnableStart = System.currentTimeMillis();
		this.logPrefix = "["+this.getDescription().getFullName()+"] ";
		log("=== ENABLE START ===");
		
		// Ensure the base folder exists
		this.getDataFolder().mkdirs();
		
		// Create Tools
		MCore.createCmd(this);
		MCore.createPersist(this);
		MCore.createTxt(this);
		MCore.createPerm(this);
		
		// Assign tool pointers
		this.cmd = MCore.getCmd(this);
		this.persist = MCore.getPersist(this);
		this.txt = MCore.getTxt(this);
		this.perm = MCore.getPerm(this);
		
		return true;
	}
	
	public void postEnable()
	{
		log("=== ENABLE DONE (Took "+(System.currentTimeMillis()-timeEnableStart)+"ms) ===");
	}
	
	// -------------------------------------------- //
	// DISABLE
	// -------------------------------------------- //
	
	public void onDisable()
	{
		MCore.getPersist(this).saveAll();
		MCore.removePersist(this);
		MCore.removeCmd(this);
		MCore.removePerm(this);
		MCore.removeTxt(this);
		
		this.cmd = null;
		this.persist = null;
		this.txt = null;
		this.perm = null;
		
		log("Disabled");
	}
	
	// -------------------------------------------- //
	// CONVENIENCE
	// -------------------------------------------- //
	
	public void suicide()
	{
		log("Now I suicide!");
		Bukkit.getPluginManager().disablePlugin(this);
	}
	
	public void registerEvent(Event.Type type, Listener listener, Event.Priority priority)
	{
		Bukkit.getPluginManager().registerEvent(type, listener, priority, this);
	}
	
	public void registerEvent(Event.Type type, Listener listener)
	{
		registerEvent(type, listener, Event.Priority.Normal);
	}
	
	// -------------------------------------------- //
	// LOGGING
	// -------------------------------------------- //
	private String logPrefix = null;
	public void log(Object... msg)
	{
		log(Level.INFO, msg);
	}
	public void log(Level level, Object... msg)
	{
		Logger.getLogger("Minecraft").log(level, this.logPrefix+txt.implode(msg, " "));
	}
}
