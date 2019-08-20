package com.lilithsthrone.game.sex.sexActions.baseActionsMisc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.CorruptionLevel;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.DialogueNodeType;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.ArousalIncrease;
import com.lilithsthrone.game.sex.Sex;
import com.lilithsthrone.game.sex.SexParticipantType;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;
import com.lilithsthrone.game.sex.positions.AbstractSexPosition;
import com.lilithsthrone.game.sex.positions.SexPositionOther;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.game.sex.positions.slots.SexSlotAgainstWall;
import com.lilithsthrone.game.sex.positions.slots.SexSlotAllFours;
import com.lilithsthrone.game.sex.positions.slots.SexSlotDesk;
import com.lilithsthrone.game.sex.positions.slots.SexSlotGeneric;
import com.lilithsthrone.game.sex.positions.slots.SexSlotMilkingStall;
import com.lilithsthrone.game.sex.positions.slots.SexSlotSitting;
import com.lilithsthrone.game.sex.positions.slots.SexSlotStanding;
import com.lilithsthrone.game.sex.positions.slots.SexSlotStocks;
import com.lilithsthrone.game.sex.sexActions.SexAction;
import com.lilithsthrone.game.sex.sexActions.SexActionType;
import com.lilithsthrone.game.sex.sexActions.SexActionUtility;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * Contains actions related to the positioning menu.
 * 
 * @since 0.3.4
 * @version 0.3.4
 * @author Innoxia
 */
public class PositioningMenu {
	
	private static AbstractSexPosition position;
	private static Map<GameCharacter, SexSlot> positioningSlots;
	private static GameCharacter targetedCharacter;
	private static List<SexSlot> availableSlots;
	
	private static StringBuilder positioningSB = new StringBuilder();
	
	public static void setNewSexManager() {
		Map<GameCharacter, SexSlot> dominants = new HashMap<>();
		Map<GameCharacter, SexSlot> submissives = new HashMap<>();
		List<GameCharacter> dominantSpectators = new ArrayList<>();
		List<GameCharacter> submissiveSpectators = new ArrayList<>();
		
		for(Entry<GameCharacter, SexSlot> e : positioningSlots.entrySet()) {
//			System.out.println(e.getKey().getName()+", "+e.getValue().getName(e.getKey()));
			if(Sex.isDom(e.getKey()) || Sex.getDominantSpectators().contains(e.getKey())) {
				if(e.getValue()==SexSlotGeneric.MISC_WATCHING) {
					dominantSpectators.add(e.getKey());
				} else {
					dominants.put(e.getKey(), e.getValue());
				}
			} else {
				if(e.getValue()==SexSlotGeneric.MISC_WATCHING) {
					submissiveSpectators.add(e.getKey());
				} else {
					submissives.put(e.getKey(), e.getValue());
				}
			}
		}
		
		Sex.setSexManager(
				new SexManagerDefault(
						position,
						dominants,
						submissives){
				},
				dominantSpectators,
				submissiveSpectators);
		
		Sex.setPositionRequest(null);
	}
	
	@SuppressWarnings("fallthrough")
	private static List<SexSlot> getAvailableSlots(GameCharacter character) {
		List<SexSlot> slotsOne = new ArrayList<>();
		List<SexSlot> slotsTwo = new ArrayList<>();
		List<SexSlot> slotsThree = new ArrayList<>();
		List<SexSlot> slotsFour = new ArrayList<>();
		List<SexSlot> slotsFive = new ArrayList<>();
		List<SexSlot> slotsSix = new ArrayList<>();
		List<SexSlot> slotsSeven = new ArrayList<>();
		Map<GameCharacter, SexSlot> doms = Sex.getDominantParticipants(true);
		Map<GameCharacter, SexSlot> subs = Sex.getSubmissiveParticipants(true);
		
		if(position==SexPositionOther.STANDING) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotStanding.STANDING_DOMINANT_FOUR);
					slotsTwo.add(SexSlotStanding.STANDING_SUBMISSIVE_FOUR);
					slotsThree.add(SexSlotStanding.PERFORMING_ORAL_FOUR);
					slotsFour.add(SexSlotStanding.PERFORMING_ORAL_BEHIND_FOUR);
				case 3: 
					slotsOne.add(SexSlotStanding.STANDING_DOMINANT_THREE);
					slotsTwo.add(SexSlotStanding.STANDING_SUBMISSIVE_THREE);
					slotsThree.add(SexSlotStanding.PERFORMING_ORAL_THREE);
					slotsFour.add(SexSlotStanding.PERFORMING_ORAL_BEHIND_THREE);
				case 2: 
					slotsOne.add(SexSlotStanding.STANDING_DOMINANT_TWO);
					slotsTwo.add(SexSlotStanding.STANDING_SUBMISSIVE_TWO);
					slotsThree.add(SexSlotStanding.PERFORMING_ORAL_TWO);
					slotsFour.add(SexSlotStanding.PERFORMING_ORAL_BEHIND_TWO);
				default:
					slotsOne.add(SexSlotStanding.STANDING_DOMINANT);
					slotsTwo.add(SexSlotStanding.STANDING_SUBMISSIVE);
					slotsThree.add(SexSlotStanding.PERFORMING_ORAL);
					slotsFour.add(SexSlotStanding.PERFORMING_ORAL_BEHIND);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
		}
		
		if(position==SexPositionOther.AGAINST_WALL) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotAgainstWall.STANDING_WALL_FOUR);
					slotsTwo.add(SexSlotAgainstWall.FACE_TO_WALL_FOUR);
					slotsThree.add(SexSlotAgainstWall.BACK_TO_WALL_FOUR);
					slotsFour.add(SexSlotAgainstWall.PERFORMING_ORAL_WALL_FOUR);
				case 3: 
					slotsOne.add(SexSlotAgainstWall.STANDING_WALL_THREE);
					slotsTwo.add(SexSlotAgainstWall.FACE_TO_WALL_THREE);
					slotsThree.add(SexSlotAgainstWall.BACK_TO_WALL_THREE);
					slotsFour.add(SexSlotAgainstWall.PERFORMING_ORAL_WALL_THREE);
				case 2: 
					slotsOne.add(SexSlotAgainstWall.STANDING_WALL_TWO);
					slotsTwo.add(SexSlotAgainstWall.FACE_TO_WALL_TWO);
					slotsThree.add(SexSlotAgainstWall.BACK_TO_WALL_TWO);
					slotsFour.add(SexSlotAgainstWall.PERFORMING_ORAL_WALL_TWO);
				default:
					slotsOne.add(SexSlotAgainstWall.STANDING_WALL);
					slotsTwo.add(SexSlotAgainstWall.FACE_TO_WALL);
					slotsThree.add(SexSlotAgainstWall.BACK_TO_WALL);
					slotsFour.add(SexSlotAgainstWall.PERFORMING_ORAL_WALL);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
		}
		
		if(position==SexPositionOther.OVER_DESK) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotDesk.BETWEEN_LEGS_FOUR);
					slotsTwo.add(SexSlotDesk.PERFORMING_ORAL_FOUR);
					slotsThree.add(SexSlotDesk.RECEIVING_ORAL_FOUR);
					slotsFour.add(SexSlotDesk.OVER_DESK_ON_BACK_FOUR);
					slotsFive.add(SexSlotDesk.OVER_DESK_ON_FRONT_FOUR);
				case 3: 
					slotsOne.add(SexSlotDesk.BETWEEN_LEGS_THREE);
					slotsTwo.add(SexSlotDesk.PERFORMING_ORAL_THREE);
					slotsThree.add(SexSlotDesk.RECEIVING_ORAL_THREE);
					slotsFour.add(SexSlotDesk.OVER_DESK_ON_BACK_THREE);
					slotsFive.add(SexSlotDesk.OVER_DESK_ON_FRONT_THREE);
				case 2: 
					slotsOne.add(SexSlotDesk.BETWEEN_LEGS_TWO);
					slotsTwo.add(SexSlotDesk.PERFORMING_ORAL_TWO);
					slotsThree.add(SexSlotDesk.RECEIVING_ORAL_TWO);
					slotsFour.add(SexSlotDesk.OVER_DESK_ON_BACK_TWO);
					slotsFive.add(SexSlotDesk.OVER_DESK_ON_FRONT_TWO);
				default:
					slotsOne.add(SexSlotDesk.BETWEEN_LEGS);
					slotsTwo.add(SexSlotDesk.PERFORMING_ORAL);
					slotsThree.add(SexSlotDesk.RECEIVING_ORAL);
					slotsFour.add(SexSlotDesk.OVER_DESK_ON_BACK);
					slotsFive.add(SexSlotDesk.OVER_DESK_ON_FRONT);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
			Collections.reverse(slotsFive);
		}
		
		if(position==SexPositionOther.STOCKS) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotStocks.BEHIND_STOCKS_FOUR);
					slotsTwo.add(SexSlotStocks.RECEIVING_ORAL_FOUR);
					slotsThree.add(SexSlotStocks.PERFORMING_ORAL_FOUR);
					slotsFour.add(SexSlotStocks.LOCKED_IN_STOCKS_FOUR);
				case 3: 
					slotsOne.add(SexSlotStocks.BEHIND_STOCKS_THREE);
					slotsTwo.add(SexSlotStocks.RECEIVING_ORAL_THREE);
					slotsThree.add(SexSlotStocks.PERFORMING_ORAL_THREE);
					slotsFour.add(SexSlotStocks.LOCKED_IN_STOCKS_THREE);
				case 2: 
					slotsOne.add(SexSlotStocks.BEHIND_STOCKS_TWO);
					slotsTwo.add(SexSlotStocks.RECEIVING_ORAL_TWO);
					slotsThree.add(SexSlotStocks.PERFORMING_ORAL_TWO);
					slotsFour.add(SexSlotStocks.LOCKED_IN_STOCKS_TWO);
				default:
					slotsOne.add(SexSlotStocks.BEHIND_STOCKS);
					slotsTwo.add(SexSlotStocks.RECEIVING_ORAL);
					slotsThree.add(SexSlotStocks.PERFORMING_ORAL);
					slotsFour.add(SexSlotStocks.LOCKED_IN_STOCKS);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
			Collections.reverse(slotsFive);
		}
		
		if(position==SexPositionOther.MILKING_STALL) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotMilkingStall.BEHIND_MILKING_STALL_FOUR);
					slotsTwo.add(SexSlotMilkingStall.RECEIVING_ORAL_FOUR);
					slotsThree.add(SexSlotMilkingStall.PERFORMING_ORAL_FOUR);
					slotsFour.add(SexSlotMilkingStall.LOCKED_IN_MILKING_STALL_FOUR);
				case 3: 
					slotsOne.add(SexSlotMilkingStall.BEHIND_MILKING_STALL_THREE);
					slotsTwo.add(SexSlotMilkingStall.RECEIVING_ORAL_THREE);
					slotsThree.add(SexSlotMilkingStall.PERFORMING_ORAL_THREE);
					slotsFour.add(SexSlotMilkingStall.LOCKED_IN_MILKING_STALL_THREE);
				case 2: 
					slotsOne.add(SexSlotMilkingStall.BEHIND_MILKING_STALL_TWO);
					slotsTwo.add(SexSlotMilkingStall.RECEIVING_ORAL_TWO);
					slotsThree.add(SexSlotMilkingStall.PERFORMING_ORAL_TWO);
					slotsFour.add(SexSlotMilkingStall.LOCKED_IN_MILKING_STALL_TWO);
				default:
					slotsOne.add(SexSlotMilkingStall.BEHIND_MILKING_STALL);
					slotsTwo.add(SexSlotMilkingStall.RECEIVING_ORAL);
					slotsThree.add(SexSlotMilkingStall.PERFORMING_ORAL);
					slotsFour.add(SexSlotMilkingStall.LOCKED_IN_MILKING_STALL);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
			Collections.reverse(slotsFive);
		}
		
		if(position==SexPositionOther.SITTING) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotSitting.SITTING_FOUR);
					slotsTwo.add(SexSlotSitting.SITTING_BETWEEN_LEGS_FOUR);
					slotsThree.add(SexSlotSitting.SITTING_IN_LAP_FOUR);
					slotsFour.add(SexSlotSitting.PERFORMING_ORAL_FOUR);
					slotsFive.add(SexSlotSitting.SITTING_TAUR_PRESENTING_ORAL_FOUR);
				case 3: 
					slotsOne.add(SexSlotSitting.SITTING_THREE);
					slotsTwo.add(SexSlotSitting.SITTING_BETWEEN_LEGS_THREE);
					slotsThree.add(SexSlotSitting.SITTING_IN_LAP_THREE);
					slotsFour.add(SexSlotSitting.PERFORMING_ORAL_THREE);
					slotsFive.add(SexSlotSitting.SITTING_TAUR_PRESENTING_ORAL_THREE);
				case 2: 
					slotsOne.add(SexSlotSitting.SITTING_TWO);
					slotsTwo.add(SexSlotSitting.SITTING_BETWEEN_LEGS_TWO);
					slotsThree.add(SexSlotSitting.SITTING_IN_LAP_TWO);
					slotsFour.add(SexSlotSitting.PERFORMING_ORAL_TWO);
					slotsFive.add(SexSlotSitting.SITTING_TAUR_PRESENTING_ORAL_TWO);
				default:
					slotsOne.add(SexSlotSitting.SITTING);
					slotsTwo.add(SexSlotSitting.SITTING_BETWEEN_LEGS);
					slotsThree.add(SexSlotSitting.SITTING_IN_LAP);
					slotsFour.add(SexSlotSitting.PERFORMING_ORAL);
					slotsFive.add(SexSlotSitting.SITTING_TAUR_PRESENTING_ORAL);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
			Collections.reverse(slotsFive);
		}
		
		if(position==SexPositionOther.ALL_FOURS) {
			int sizeSlots = (doms.size()+subs.size())-1;
			switch(sizeSlots) {
				case 4:
					slotsOne.add(SexSlotAllFours.ALL_FOURS_FOUR);
					slotsTwo.add(SexSlotAllFours.BEHIND_FOUR);
					slotsThree.add(SexSlotAllFours.BEHIND_ORAL_FOUR);
					slotsFour.add(SexSlotAllFours.HUMPING_FOUR);
					slotsFive.add(SexSlotAllFours.USING_FEET_FOUR);
					slotsSix.add(SexSlotAllFours.IN_FRONT_FOUR);
					slotsSeven.add(SexSlotAllFours.IN_FRONT_ANAL_FOUR);
				case 3: 
					slotsOne.add(SexSlotAllFours.ALL_FOURS_THREE);
					slotsTwo.add(SexSlotAllFours.BEHIND_THREE);
					slotsThree.add(SexSlotAllFours.BEHIND_ORAL_THREE);
					slotsFour.add(SexSlotAllFours.HUMPING_THREE);
					slotsFive.add(SexSlotAllFours.USING_FEET_THREE);
					slotsSix.add(SexSlotAllFours.IN_FRONT_THREE);
					slotsSeven.add(SexSlotAllFours.IN_FRONT_ANAL_THREE);
				case 2:
					slotsOne.add(SexSlotAllFours.ALL_FOURS_TWO);
					slotsTwo.add(SexSlotAllFours.BEHIND_TWO);
					slotsThree.add(SexSlotAllFours.BEHIND_ORAL_TWO);
					slotsFour.add(SexSlotAllFours.HUMPING_TWO);
					slotsFive.add(SexSlotAllFours.USING_FEET_TWO);
					slotsSix.add(SexSlotAllFours.IN_FRONT_TWO);
					slotsSeven.add(SexSlotAllFours.IN_FRONT_ANAL_TWO);
				default:
					slotsOne.add(SexSlotAllFours.ALL_FOURS);
					slotsTwo.add(SexSlotAllFours.BEHIND);
					slotsThree.add(SexSlotAllFours.BEHIND_ORAL);
					slotsFour.add(SexSlotAllFours.HUMPING);
					slotsFive.add(SexSlotAllFours.USING_FEET);
					slotsSix.add(SexSlotAllFours.IN_FRONT);
					slotsSeven.add(SexSlotAllFours.IN_FRONT_ANAL);
			}
			Collections.reverse(slotsOne);
			Collections.reverse(slotsTwo);
			Collections.reverse(slotsThree);
			Collections.reverse(slotsFour);
			Collections.reverse(slotsFive);
			Collections.reverse(slotsSix);
			Collections.reverse(slotsSeven);
		}

		List<SexSlot> slots = new ArrayList<>(slotsOne);
		slots.addAll(slotsTwo);
		slots.addAll(slotsThree);
		slots.addAll(slotsFour);
		slots.addAll(slotsFive);

		slots.add(SexSlotGeneric.MISC_WATCHING);
		
		return slots;
	}
	
	private static Value<Boolean, String> isSlotsAcceptable() {
		if(!Sex.isMasturbation()) {
			boolean domsAcceptable = false;
			for(GameCharacter c : Sex.getDominantParticipants(true).keySet()) {
				if(positioningSlots.get(c)!=SexSlotGeneric.MISC_WATCHING) {
					domsAcceptable = true;
					break;
				}
			}
			boolean subsAcceptable = false;
			for(GameCharacter c : Sex.getSubmissiveParticipants(true).keySet()) {
				if(positioningSlots.get(c)!=SexSlotGeneric.MISC_WATCHING) {
					subsAcceptable = true;
					break;
				}
			}
			if(!(domsAcceptable && subsAcceptable)) {
				return new Value<Boolean, String>(false, "You need to have at least one dominant and one submissive character in non-spectator slots.");
			}
		}

		return position.isAcceptablePosition(positioningSlots);
	}
	
	public static final DialogueNode POSITIONING_MENU = new DialogueNode("Positioning Selection", "", true) {
		@Override
		public String getLabel() {
			return "Positioning Selection: "+position.getName();
		}
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p style='text-align:center;'>");

			if(Sex.getDominantParticipants(true).size()>0) {
				UtilText.nodeContentSB.append("[style.boldDominant(Dominants)]:</b>");
					for(GameCharacter c : Sex.getDominantParticipants(true).keySet()) {
						UtilText.nodeContentSB.append(UtilText.parse(c,
								"<br/><b style='color:"+c.getFemininity().getColour().toWebHexString()+";'>[npc.Name]</b><b>:</b> "
										+(positioningSlots.get(c)==SexSlotGeneric.MISC_WATCHING
											?"[style.colourDisabled("+Util.capitaliseSentence(positioningSlots.get(c).getDescription())+")]"
											:Util.capitaliseSentence(positioningSlots.get(c).getDescription()))));
					}
			}

			if(Sex.getSubmissiveParticipants(true).size()>0) {
				UtilText.nodeContentSB.append("<br/><br/>[style.boldSubmissive(Submissives)]:</b>");
					for(GameCharacter c : Sex.getSubmissiveParticipants(true).keySet()) {
						UtilText.nodeContentSB.append(UtilText.parse(c,
								"<br/><b style='color:"+c.getFemininity().getColour().toWebHexString()+";'>[npc.Name]</b><b>:</b> "
										+(positioningSlots.get(c)==SexSlotGeneric.MISC_WATCHING
											?"[style.colourDisabled("+Util.capitaliseSentence(positioningSlots.get(c).getDescription())+")]"
											:Util.capitaliseSentence(positioningSlots.get(c).getDescription()))));
					}
			}
			UtilText.nodeContentSB.append("</p>");
			
			Value<Boolean, String> acceptableValue = isSlotsAcceptable();
			if(!acceptableValue.getKey()) {
				UtilText.nodeContentSB.append(
						"<p style='text-align:center;'>"
								+ "[style.boldBad(Invalid position)]<b>:</b><br/>"
								+"[style.italicsMinorBad("+acceptableValue.getValue()+")]"
						+ "</p>");
			} else {
				UtilText.nodeContentSB.append(
						"<p style='text-align:center;'>"
								+ "<b>Projected outcome:</b><br/>"
								+"<i>"+position.getDescription(positioningSlots)+"</i>"
						+ "</p>");
			}
			
			
			// TODO help text
			
			// Commented-out code is for displaying what interactions are available to whom, but it ended up getting far too verbose.
			/*
			Map<GameCharacter, Map<GameCharacter, SexActionInteractions>> interactions = new HashMap<>();
			
			for(Entry<SexSlot, GameCharacter> e1 : positioningSlots.entrySet()) {
				for(Entry<SexSlot, GameCharacter> e2 : positioningSlots.entrySet()) {
					if(!e1.equals(e2)) {
						interactions.put(e1.getValue(), new HashMap<>());
						interactions.get(e1.getValue()).put(e2.getValue(), position.getSexInteractions(e1.getKey(), e2.getKey()));
					}
				}
			}

			for(Entry<GameCharacter, Map<GameCharacter, SexActionInteractions>> e : interactions.entrySet()) {
				UtilText.nodeContentSB.append("<p>");
				for(Entry<GameCharacter, SexActionInteractions> e2 : e.getValue().entrySet()) {
					UtilText.nodeContentSB.append(UtilText.parse(e.getKey(), e2.getKey(),
							"[npc.Name] can interact with [npc2.name] in the following ways:"));
					for(Entry<SexAreaInterface, List<SexAreaInterface>> interactionEntry : e2.getValue().getInteractions().entrySet()) {
						UtilText.nodeContentSB.append("<br/>"+interactionEntry.getKey().getName(e.getKey())+" to ");
						List<String> areaNames = new ArrayList<>();
						for(SexAreaInterface targetedArea : interactionEntry.getValue()) {
							areaNames.add(targetedArea.getName(e2.getKey()));
						}
						UtilText.nodeContentSB.append(Util.stringsToStringList(areaNames, false)+".");
					}
				}
				UtilText.nodeContentSB.append("</p>");
			}
			*/
			
			return UtilText.nodeContentSB.toString();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index == 0) {
				return new ResponseEffectsOnly("Back", "Decide against changing position."){
					@Override
					public void effects() {
						Main.game.restoreSavedContent(false);
					}
				};
				
			} else if(index==1) {
				Value<Boolean, String> acceptableValue = isSlotsAcceptable();
				if(!acceptableValue.getKey()) {
					return new Response("Accept", acceptableValue.getValue(), null);
				}
				return new Response("Accept", "Move into the selected position.", Sex.SEX_DIALOGUE){
					@Override
					public boolean isSexPositioningHighlight() {
						return true;
					}
					@Override
					public void effects(){
						positioningSB.setLength(0);
						
						positioningSB.append(
								"Wanting to switch into a new position, "
									+(Sex.isMasturbation()
										?"you move around until you've assumed the posture you had in mind..."
										:(Sex.getAllParticipants(false).size()==2
											?UtilText.parse(Sex.getTargetedPartner(Main.game.getPlayer()), "you manoeuvre [npc.name] into [npc.her] new place")
											:"you manoeuvre your partners into their new places")
										+", before assuming the posture you had in mind..."));

						Sex.endSexTurn(SexActionUtility.POSITION_SELECTION);
					}
				};
				
			} else if(index==2) {
				return new Response(
						"Target: <b style='color:"+targetedCharacter.getFemininity().getColour().toWebHexString()+";'>"+UtilText.parse(targetedCharacter, "[npc.Name]")+"</b>",
						"This is the character you're currently choosing a slot for.",
						POSITIONING_MENU){
					@Override
					public void effects() {
						List<GameCharacter> characters = Sex.getAllParticipants(true);
						
						for(int i=0; i<characters.size(); i++) {
							if(characters.get(i).equals(targetedCharacter)) {
								if(i==characters.size()-1) {
									targetedCharacter = characters.get(0);
									break;
								} else {
									targetedCharacter = characters.get(i+1);
									break;
								}
							}
						}
						
						availableSlots = getAvailableSlots(targetedCharacter);
					}
				};
				
			} else if(index-3<availableSlots.size()) {
				SexSlot slot = availableSlots.get(index-3);
				
				GameCharacter characterInSlot = null;
				for(Entry<GameCharacter, SexSlot> e : positioningSlots.entrySet()) {
					if(e.getValue().equals(slot)) {
						characterInSlot = e.getKey();
						break;
					}
				}
				
				if(targetedCharacter.equals(characterInSlot)) {
					return new Response(Util.capitaliseSentence(slot.getDescription()), UtilText.parse(targetedCharacter, "[npc.NameIsFull] already in this slot..."), null);
				}
				
				if(!position.isSlotUnlocked(targetedCharacter, slot, positioningSlots).getKey()) {
					return new Response(Util.capitaliseSentence(slot.getDescription()), position.isSlotUnlocked(targetedCharacter, slot, positioningSlots).getValue(), null);
				}
				
				return new Response(
						characterInSlot!=null && slot!=SexSlotGeneric.MISC_WATCHING
							?"<span style='color:"+characterInSlot.getFemininity().getColour().toWebHexString()+";'>"+Util.capitaliseSentence(slot.getDescription())+"</span>"
							:Util.capitaliseSentence(slot.getDescription()),
						characterInSlot!=null && slot!=SexSlotGeneric.MISC_WATCHING
							?UtilText.parse(targetedCharacter, characterInSlot,
									"[npc2.NameIsFull] already assigned to this slot, so by selecting [npc.name] to take this slot, you will swap the two of them around."
									+ " (Resulting in [npc2.name] being in the '"+positioningSlots.get(targetedCharacter).getDescription()+"' slot.)")
							:UtilText.parse(targetedCharacter, "Assign [npc.name] to this slot."),
						POSITIONING_MENU){
					@Override
					public void effects() {
						if(slot!=SexSlotGeneric.MISC_WATCHING) {
							GameCharacter characterToSwap = null;
							for(Entry<GameCharacter, SexSlot> e : positioningSlots.entrySet()) {
								if(e.getValue().equals(slot)) {
									characterToSwap = e.getKey();
									break;
								}
							}
							if(characterToSwap!=null) {
								positioningSlots.put(characterToSwap, positioningSlots.get(targetedCharacter));
							}
						}
						positioningSlots.put(targetedCharacter, slot);
					}
				};
				
			}  else {
				return null;
			}
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.PHONE;
		}
	};
	
	public static final SexAction OPEN_MENU_STANDING = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() {
			return Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.STANDING.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.STANDING.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.STANDING;
			SexSlot[] domSlots = new SexSlot[] {SexSlotStanding.STANDING_DOMINANT, SexSlotStanding.STANDING_DOMINANT_TWO, SexSlotStanding.STANDING_DOMINANT_THREE, SexSlotStanding.STANDING_DOMINANT_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotStanding.STANDING_SUBMISSIVE, SexSlotStanding.STANDING_SUBMISSIVE_TWO, SexSlotStanding.STANDING_SUBMISSIVE_THREE, SexSlotStanding.STANDING_SUBMISSIVE_FOUR};
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.STANDING) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				for(int i=0; i<doms.size(); i++) {
					positioningSlots.put(doms.get(i), domSlots[i]);
				}
				for(int i=0; i<subs.size(); i++) {
					positioningSlots.put(subs.get(i), subSlots[i]);
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}
			
			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};
	
	public static final SexAction OPEN_MENU_AGAINST_WALL = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() {
			return Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.AGAINST_WALL.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.AGAINST_WALL.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.AGAINST_WALL;
			SexSlot[] domSlots = new SexSlot[] {SexSlotAgainstWall.STANDING_WALL, SexSlotAgainstWall.STANDING_WALL_TWO, SexSlotAgainstWall.STANDING_WALL_THREE, SexSlotAgainstWall.STANDING_WALL_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotAgainstWall.FACE_TO_WALL, SexSlotAgainstWall.FACE_TO_WALL_TWO, SexSlotAgainstWall.FACE_TO_WALL_THREE, SexSlotAgainstWall.FACE_TO_WALL_FOUR};
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.AGAINST_WALL) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				for(int i=0; i<doms.size(); i++) {
					positioningSlots.put(doms.get(i), domSlots[i]);
				}
				for(int i=0; i<subs.size(); i++) {
					positioningSlots.put(subs.get(i), subSlots[i]);
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}

			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};
	
	public static final SexAction OPEN_MENU_SITTING = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() {
			int taurs = 0;
			for(GameCharacter participant : Sex.getAllParticipants(true)) {
				if(!participant.getLegConfiguration().isBipedalPositionedGenitals()) {
					taurs++;
				}
			}
			return Sex.getAllParticipants(true).size()!=taurs // Need at least one non-taur
					&& taurs<=4 // Cannot have more than 4 taurs
					&& Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.SITTING.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.SITTING.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.SITTING;
			SexSlot[] domSlots = new SexSlot[] {SexSlotSitting.SITTING, SexSlotSitting.SITTING_TWO, SexSlotSitting.SITTING_THREE, SexSlotSitting.SITTING_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotSitting.SITTING_IN_LAP, SexSlotSitting.SITTING_IN_LAP_TWO, SexSlotSitting.SITTING_IN_LAP_THREE, SexSlotSitting.SITTING_IN_LAP_FOUR};
			
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.SITTING) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				int sittingCount = 0;
				int inLapCount = 0;
				
				for(int i=0; i<doms.size(); i++) {
					if(doms.get(i).getLegConfiguration().isBipedalPositionedGenitals()) {
						positioningSlots.put(doms.get(i), domSlots[sittingCount]);
						sittingCount++;
					} else {
						positioningSlots.put(doms.get(i), subSlots[inLapCount]);
						inLapCount++;
					}
				}
				for(int i=0; i<subs.size(); i++) {
					if(subs.get(i).getLegConfiguration().isBipedalPositionedGenitals()) {
						positioningSlots.put(subs.get(i), domSlots[sittingCount]);
						sittingCount++;
					} else {
						positioningSlots.put(subs.get(i), subSlots[inLapCount]);
						inLapCount++;
					}
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}

			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};
	
	public static final SexAction OPEN_MENU_OVER_DESK = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() {
			return Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.OVER_DESK.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.OVER_DESK.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.OVER_DESK;
			SexSlot[] domSlots = new SexSlot[] {SexSlotDesk.BETWEEN_LEGS, SexSlotDesk.BETWEEN_LEGS_TWO, SexSlotDesk.BETWEEN_LEGS_THREE, SexSlotDesk.BETWEEN_LEGS_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotDesk.OVER_DESK_ON_FRONT, SexSlotDesk.OVER_DESK_ON_FRONT_TWO, SexSlotDesk.OVER_DESK_ON_FRONT_THREE, SexSlotDesk.OVER_DESK_ON_FRONT_FOUR};
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.OVER_DESK) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				for(int i=0; i<doms.size(); i++) {
					positioningSlots.put(doms.get(i), domSlots[i]);
				}
				for(int i=0; i<subs.size(); i++) {
					positioningSlots.put(subs.get(i), subSlots[i]);
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}

			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};
	
	public static final SexAction OPEN_MENU_STOCKS = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() { //TODO limit to already in stocks
			return Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.STOCKS.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.STOCKS.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.STOCKS;
			SexSlot[] domSlots = new SexSlot[] {SexSlotStocks.BEHIND_STOCKS, SexSlotStocks.BEHIND_STOCKS_TWO, SexSlotStocks.BEHIND_STOCKS_THREE, SexSlotStocks.BEHIND_STOCKS_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotStocks.LOCKED_IN_STOCKS, SexSlotStocks.LOCKED_IN_STOCKS_TWO, SexSlotStocks.LOCKED_IN_STOCKS_THREE, SexSlotStocks.LOCKED_IN_STOCKS_FOUR};
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.STOCKS) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				for(int i=0; i<doms.size(); i++) {
					positioningSlots.put(doms.get(i), domSlots[i]);
				}
				for(int i=0; i<subs.size(); i++) {
					positioningSlots.put(subs.get(i), subSlots[i]);
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}

			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};

	public static final SexAction OPEN_MENU_MILKING_STALL = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() { //TODO limit to already in milking stall scene
			return Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.MILKING_STALL.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.MILKING_STALL.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.MILKING_STALL;
			SexSlot[] domSlots = new SexSlot[] {SexSlotMilkingStall.BEHIND_MILKING_STALL, SexSlotMilkingStall.BEHIND_MILKING_STALL_TWO, SexSlotMilkingStall.BEHIND_MILKING_STALL_THREE, SexSlotMilkingStall.BEHIND_MILKING_STALL_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotMilkingStall.LOCKED_IN_MILKING_STALL, SexSlotMilkingStall.LOCKED_IN_MILKING_STALL_TWO, SexSlotMilkingStall.LOCKED_IN_MILKING_STALL_THREE, SexSlotMilkingStall.LOCKED_IN_MILKING_STALL_FOUR};
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.MILKING_STALL) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				for(int i=0; i<doms.size(); i++) {
					positioningSlots.put(doms.get(i), domSlots[i]);
				}
				for(int i=0; i<subs.size(); i++) {
					positioningSlots.put(subs.get(i), subSlots[i]);
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}

			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};
	
	public static final SexAction OPEN_MENU_ALL_FOURS = new SexAction(
			SexActionType.POSITIONING_MENU,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			null,
			SexParticipantType.NORMAL) {

		@Override
		public boolean isBaseRequirementsMet() {
			return Sex.getCharacterPerformingAction().isPlayer();
		}
		
		@Override
		public String getActionTitle() {
			return Util.capitaliseSentence(SexPositionOther.ALL_FOURS.getName());
		}

		@Override
		public String getActionDescription() {
			return "Open the positioning menu for all sex positions related to the '"+Util.capitaliseSentence(SexPositionOther.ALL_FOURS.getName())+"' core position.";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void applyEffects() {
			position = SexPositionOther.ALL_FOURS;
			SexSlot[] domSlots = new SexSlot[] {SexSlotAllFours.BEHIND, SexSlotAllFours.BEHIND_TWO, SexSlotAllFours.BEHIND_THREE, SexSlotAllFours.BEHIND_FOUR};
			SexSlot[] subSlots = new SexSlot[] {SexSlotAllFours.ALL_FOURS, SexSlotAllFours.ALL_FOURS_TWO, SexSlotAllFours.ALL_FOURS_THREE, SexSlotAllFours.ALL_FOURS_FOUR};
			List<GameCharacter> doms = new ArrayList<>(Sex.getDominantParticipants(false).keySet());
			List<GameCharacter> subs = new ArrayList<>(Sex.getSubmissiveParticipants(false).keySet());
			
			if(Sex.getSexManager().getPosition()==SexPositionOther.ALL_FOURS) {
				positioningSlots = new HashMap<>(Sex.getAllOccupiedSlots(true));
				
			} else {
				positioningSlots = new HashMap<>();
				for(int i=0; i<doms.size(); i++) {
					positioningSlots.put(doms.get(i), domSlots[i]);
				}
				for(int i=0; i<subs.size(); i++) {
					positioningSlots.put(subs.get(i), subSlots[i]);
				}
				
				for(GameCharacter c : Sex.getDominantSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
				for(GameCharacter c : Sex.getSubmissiveSpectators()) {
					positioningSlots.put(c, SexSlotGeneric.MISC_WATCHING);
				}
			}

			targetedCharacter = Main.game.getPlayer();//Sex.getCharacterTargetedForSexAction(this);
			availableSlots = getAvailableSlots(targetedCharacter);
			
			Main.mainController.openPhone(POSITIONING_MENU);
		}
	};
}
