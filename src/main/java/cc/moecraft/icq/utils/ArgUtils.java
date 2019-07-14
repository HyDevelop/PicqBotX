package cc.moecraft.icq.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.moecraft.icq.sender.message.MessageComponent;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.sender.message.components.ComponentBFace;
import cc.moecraft.icq.sender.message.components.ComponentDice;
import cc.moecraft.icq.sender.message.components.ComponentEmoji;
import cc.moecraft.icq.sender.message.components.ComponentFace;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.sender.message.components.ComponentMusic;
import cc.moecraft.icq.sender.message.components.ComponentMusic.MusicSourceType;
import cc.moecraft.icq.sender.message.components.ComponentRecord;
import cc.moecraft.icq.sender.message.components.ComponentRockPaperSissors;
import cc.moecraft.icq.sender.message.components.ComponentSFace;
import cc.moecraft.icq.sender.message.components.ComponentShake;
import cc.moecraft.icq.sender.message.components.ComponentShare;
import cc.moecraft.icq.sender.message.components.ComponentString;

/**
 * Utils for handling command arguments.
 * @author Taskeren
 *
 */
public class ArgUtils {
	
	/**
	 * 正则用的玩意。
	 */
	private static final Pattern PATTERN_CQ = Pattern.compile("\\[([^}]*)\\]");

	/**
	 * 将参数解析一下
	 * @author Taskeren
	 * @param origin 原参数
	 * @return 解析完的参数
	 */
	public static ArrayList<MessageComponent> doIt(ArrayList<String> origin){
		ArrayList<MessageComponent> ret = new ArrayList<>();
		
		for(String part : origin) {
			
			int count = 0;
			
			Matcher matcher = PATTERN_CQ.matcher(part);
			
			while(matcher.find()) {
				String group = matcher.group(); // 一组 CQ 码，例如“[CQ:at,qq=123456]”
				String s0 = group.substring(1, group.length()-1); // 去头去尾，可以吃了 /w/
				String[] s1 = s0.split(","); // 切开来，就像这样：“["CQ:at", "qq=123456"]”
				
				HashMap<String, String> data = genMap(s1);
				
				data.forEach((a, b)-> System.out.println(a+" = "+b));
				
				// 下面先获取CQ码类型
				String CQType = data.get("CQ");
				
				MessageComponent mc;
				
				switch(CQType) {
				case "face":
					// [CQ:face,id={1}]
					mc = new ComponentFace(Parser.Int(data.get("id")));
					break;
				case "emoji":
					// [CQ:emoji,id={1}]
					mc = new ComponentEmoji(Parser.Int(data.get("id")));
					break;
				case "bface":
					// [CQ:bface,id={1}]
					mc = new ComponentBFace(Parser.Int(data.get("id")));
					break;
				case "sface":
					// [CQ:sface,id={1}]
					mc = new ComponentSFace(Parser.Int(data.get("id")));
					break;
				case "image":
					// [CQ:image,file={1}]
					mc = new ComponentImage(data.get("url"));
					break;
				case "record":
					// [CQ:record,file={1},magic={2}]
					mc = new ComponentRecord(data.get("file"));
					break;
				case "at":
					// [CQ:at,qq={1}]
					mc = new ComponentAt(Parser.Long(data.get("qq")));
					break;
				case "rps":
					// [CQ:rps,type={1}]
					mc = new ComponentRockPaperSissors();
					((ComponentRockPaperSissors) mc).type = Parser.Int(data.get("type"));
					break;
				case "dice":
					// [CQ:dice,type={1}]
					mc = new ComponentDice();
					((ComponentDice) mc).type = Parser.Int(data.get("type"));
					break;
				case "shake":
					// [CQ:shake]
					mc = new ComponentShake();
					break;
				case "music":
					// [CQ:music,type={1},id={2}]
					mc = new ComponentMusic(Parser.Int(data.get("id")), MusicSourceType.of(data.get("type")));
					break;
				case "share":
					// [CQ:share,url={1},title={2},content={3},image={4}]
					mc = new ComponentShare(data.get("url"), data.get("title"), data.get("content"), data.get("image"));
					break;
				default:
					mc = new ComponentString(group);
					break;
				}
				
				ret.add(mc);
				count += 1;
				
			}
			
			if(count > 0) {
				
			} else {
				ret.add(new ComponentString(part));
			}
		}
		
		return ret;
	}
	
	/**
	 * 把数据搞成HashMap
	 * @author Taskeren
	 * @param array 数据
	 * @return 返回的Map
	 */
	private static HashMap<String, String> genMap(String[] array) {
		HashMap<String, String> map = new HashMap<>();
		
		for(String s : array) {
			String[] s0 = s.split(":|=", 2);
			map.put(s0[0], s0[1]);
		}
		
		return map;
	}
	
}
