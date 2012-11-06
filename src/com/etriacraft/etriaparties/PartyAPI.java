package com.etriacraft.etriaparties;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.wrappers.Party;

public class PartyAPI {

    private static ArrayList<Party> parties = new ArrayList<Party>();

    private static ArrayList<Player> directedChat = new ArrayList<Player>();

    public static void addParty(final Party p) {
        PartyAPI.parties.add(p);
    }

    public static void removeParty(final Party p) {
        PartyAPI.parties.remove(p);
    }

    public static boolean contains(final Party p) {
        return PartyAPI.parties.contains(p);
    }

    public static boolean contains(final String partyname) {
        for (final Party p : PartyAPI.parties) {
            if (p.getName().equalsIgnoreCase(partyname)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsMember(final Party p, final Player member) {
        return p.containsMember(member);
    }

    public static boolean inParty(final Player player) {
        for (final Party p : PartyAPI.parties) {
            if (p.containsMember(player)) {
                return true;
            }
        }
        return false;
    }

    public static Party getParty(final Player player) {
        for (final Party p : PartyAPI.parties) {
            if (p.containsMember(player)) {
                return p;
            }
        }
        return null;
    }

    public static Party getParty(final String partyname) {
        for (final Party p : PartyAPI.parties) {
            if (p.getName().equalsIgnoreCase(partyname)) {
                return p;
            }
        }
        return null;
    }

    public static boolean getPartyChatMode(final Player player) {
        return PartyAPI.directedChat.contains(player);
    }

    public static void setPartyChatMode(final Player player,
            final boolean partyChat) {
        if (partyChat) {
            if (!PartyAPI.directedChat.contains(player)) {
                PartyAPI.directedChat.add(player);
            }
        } else {
            if (PartyAPI.directedChat.contains(player)) {
                PartyAPI.directedChat.remove(player);
            }
        }
    }

    public static String listParties() {
        String s = ChatColor.GREEN + "Parties (" + PartyAPI.parties.size()
                + "): ";
        for (final Party party : PartyAPI.parties) {
            if (party.isInviteOnly()) {
                s += ChatColor.BLUE + party.getName() + ", ";
            } else if (!party.getPassword().equalsIgnoreCase("")) {
                s += ChatColor.RED + party.getName() + ", ";
            } else {
                s += ChatColor.GREEN + party.getName() + ", ";
            }
        }
        return s.substring(0, s.length() - 2);
    }

    public static boolean exists(final String party) {
        return PartyAPI.getParty(party) != null;
    }
}