//The "ByteManager" class provides functionality for embedding and extracting data within an array of integers using bit manipulation techniques. 
//It introduces a flag-based approach to determine the embedding pattern, allowing flexibility to change the flag value and pattern strategy. 
//The class offers methods to embed alien data into native data and retrieve the embedded data, ensuring seamless integration and extraction of information.
public class ByteManager {

	static private int flag = 0;
	static private ByteEmbedderPatternStrategy patternStrategy;

	static {
		flag = 0;
		patternStrategy = ByteEmbedderPatternStrategy.KEEP_SAME;
	}

	private static void updateFlag() {
		if(patternStrategy == ByteEmbedderPatternStrategy.INCREMENTAL_1) {
			flag = (flag+1)%3;
		} else if(patternStrategy == ByteEmbedderPatternStrategy.INCREMENTAL_2) {
			flag = (flag+2)%3;
		}
	}

	static int[] embedAlienData(int alienData, int[] nativeData){

		int noOfBitToEmbed;

		for(int i=0;i<3;i++) {
			noOfBitToEmbed = (i==flag)?2:3;
			nativeData[i] >>= noOfBitToEmbed;
			nativeData[i] <<= noOfBitToEmbed;
			nativeData[i] |= (alienData&(1<<noOfBitToEmbed)-1);
			alienData >>= noOfBitToEmbed;
		}

		updateFlag();

		return nativeData;
	}


	public static int getAlienData(int[] arg){

		int extractedData = 0, bitsAddedTillNow = 0, noOfBitsToAdd;

		for(int i=0;i<3;i++) {
			noOfBitsToAdd = (flag == i)?2:3;
			extractedData |= (arg[i]&(1<<noOfBitsToAdd)-1) << bitsAddedTillNow;
			bitsAddedTillNow += noOfBitsToAdd;
		}

		updateFlag();

		return extractedData;
	}

	public static void setFlag(int flag) {
		ByteManager.flag = flag % 3;
	}

	public static int getFlag(){
		return ByteManager.flag;
	}

	public static void setPatternStrategy(ByteEmbedderPatternStrategy newPatternStrategy) {
		patternStrategy = newPatternStrategy;
	}

	public static ByteEmbedderPatternStrategy getPatternStrategy() {
		return patternStrategy;
	}

}
