import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class renderApp {

	public static void main(String[] args) throws IOException, InterruptedException {


		File file = new File("C:\\Users\\Liam\\Desktop\\Desktop\\Programming Projects\\FanArt TV Scrape\\FanArt TV Shows Image Links\\t"
				+ ".txt"); 
		String ans = "";

		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) {
			ans = ans + st + "\n";
		} 

		String [] final1 = ans.split("\n");
		int counter = 1;

		for (int i = 1663; i < final1.length; i++) {


			String dataToScrape = "https://fanart.tv" + final1[i];
			System.out.println(dataToScrape);
			//Thread.sleep(100);
			int folderChecker = 0;
			boolean folderDup = false;

			LinkedList<String> posterLink = new LinkedList<String>();

			URL url2 = new URL (dataToScrape);

			Document doc = Jsoup.connect(url2.toString()).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36").ignoreHttpErrors(true).get();

			String docForRegex = doc.toString();
			//System.out.println(docForRegex);


			Pattern linkPattern = Pattern.compile("(?<=<h1>)(.*)(?=<span>)");
			Matcher titleMatcher = linkPattern.matcher(docForRegex);
			while(titleMatcher.find()){
				String movieTitle = titleMatcher.group();
				movieTitle = movieTitle.replace(":", " ");

				Pattern linkPattern2 = Pattern.compile("(?<=<a rel=\"tvposter\" href=\")(.*)(?=\" class=\"bigger_image\">)|(?<=<a rel=\"seasonposter\" href=\")(.*)(?=\" class=\"bigger_image\">)");
				Matcher posterLinkMatcher = linkPattern2.matcher(docForRegex);

				int folderCount = 1; 
				int fileCount = 1;
				int dupCounter = 0;



				while(posterLinkMatcher.find()){
					URL url3 = new URL ("https://fanart.tv" + posterLinkMatcher.group());
					InputStream in = new BufferedInputStream(url3.openStream());
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int n = 0;
					while (-1!=(n=in.read(buf)))
					{
						out.write(buf, 0, n);
					}
					out.close();
					in.close();
					byte[] response = out.toByteArray();
					String fileName[] = posterLinkMatcher.group().split("/");
					String folderName = movieTitle;
					int nameLength = folderName.length();
					folderName = (String) folderName.subSequence(0, nameLength-1);
					folderName = folderName.replaceAll("\\/", "");
					folderName = folderName.replaceAll("\\\\", "");
					folderName = folderName.replaceAll(":","");
					folderName = folderName.replaceAll("\\.","");
					folderName = folderName.replaceAll("\\?","");
					folderName = folderName.replaceAll("\\*","");

					while(folderChecker < 1) {

						if(new File("C:\\Users\\Liam\\Desktop\\Office" + folderName).exists() == true ) {
							folderDup = true;
							dupCounter = dupCounter + 1;

						}
						folderChecker++;
					}

					if (folderDup == true) {
						
						/*new File(C:\\Users\\Liam\\Desktop\\Office" + folderName + "(" + dupCounter + ")").mkdir();
						new File(C:\\Users\\Liam\\Desktop\\Office" + folderName + "(" + dupCounter + ")" + "/" + folderCount).mkdir();
						FileOutputStream fos = new FileOutputStream(C:\\Users\\Liam\\Desktop\\Office" + folderName + "(" + dupCounter + ")" + "/" + folderCount + "/" +  folderName + " " +  fileCount + ".jpg");
						fileCount++;
						folderCount++;
						fos.write(response);
						folderChecker++; */
					}


					else {
						new File("C:\\Users\\Liam\\Desktop\\Office" + folderName).mkdir();
						new File("C:\\Users\\Liam\\Desktop\\Office" + folderName + "/" + folderCount).mkdir();
						FileOutputStream fos = new FileOutputStream("C:\\Users\\Liam\\Desktop\\Office" + folderName + "/" + folderCount + "/" +  folderName + " " +  fileCount + ".jpg");
						fileCount++;
						folderCount++;
						fos.write(response);
						folderChecker++;
					}


					/*new File(C:\\Users\\Liam\\Desktop\\Office" + folderName).mkdir();
					new File(C:\\Users\\Liam\\Desktop\\Office" + folderName + "/" + folderCount).mkdir();
					FileOutputStream fos = new FileOutputStream(C:\\Users\\Liam\\Desktop\\Office" + folderName + "/" + folderCount + "/" +  folderName + " " +  fileCount + ".jpg");
					fileCount++;
					folderCount++;
					fos.write(response); */

				}
			}
			System.out.println(counter + " Done!");
			counter = counter+1;
		}
	}
}





