package org.example;

import java.util.*;

public class GroupManager {
	private static final Map<String, Set<String>> groups = Collections.synchronizedMap(new HashMap<>());

	public void createGroup(String groupId, String creator, String[] members){
		Set<String> member = new HashSet<>(Arrays.asList(members));
		member.add(creator);
		groups.put(groupId, member);
	}

	public Set<String> getGroupMembers(String groupId){
		return groups.get(groupId);
	}

}
