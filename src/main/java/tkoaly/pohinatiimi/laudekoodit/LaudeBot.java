package tkoaly.pohinatiimi.laudekoodit;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class LaudeBot extends TelegramLongPollingBot {

    private Dotenv dotenv = Dotenv.load();
    private Random arpoja = new Random();
    private SendMessage answer;

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        this.answer = new SendMessage();
        System.out.println(chatId);
                this.answer.setChatId(chatId);

        vastaa(text);

    }

    public void laheta(String viesti){

        answer.setText(viesti);

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void vastaa( String teksti){

        int luku;
        switch (teksti){
            case "Minne mennä?":
                luku = arpoja.nextInt(2);
                if (luku > 0) {
                    laheta("Ole laiska, mene Exaan!");
                } else {
                    laheta("Mielesi tarvitsee raitista ilmaa, mene Chemicumiin!");
                }
                break;
            default:
                StringBuilder ohjeet = new StringBuilder();
                ohjeet.append("Tänään " + teksti + " syö");
                double hinta;
                luku = arpoja.nextInt(10);

                if (luku > 1) {
                    hinta = 2.60;
                    ohjeet.append(" kuin opiskelija eli edullisesti.");

                    laheta(ohjeet.toString());
                    ohjeet = new StringBuilder();

                    if (luku > 3){
                        laheta("Mennään kasvismeiningillä!");
                        laheta("Ruuaksesi valikoitui " + randomAteria("kasvise.txt"));
                    } else {
                        laheta("Mennään lihameiningillä!");
                        laheta("Ruuaksesi valikoitui " + randomAteria("lihae.txt"));
                    }

                } else {
                    hinta = 4.60;
                    ohjeet.append(" kuin kuningas eli maukkaasti.");

                    laheta(ohjeet.toString());
                    ohjeet = new StringBuilder();

                    laheta("Mennään lihameiningillä!");

                    laheta("Ruuaksesi valikoitui " + randomAteria("maukkaasti.txt"));
                }


                ohjeet.append("Tyrehdytät janosi juomalla ");

                ArrayList<String> juomat = new ArrayList<>(Arrays.asList("vettä", "vettä", "vettä", "vettä", "vettä", "vettä", "vettä", "vettä", "vettä", "mehua", "mehua", "mehua", "maitoa", "maitoa", "piimää"));

                String juoma1 = juomat.get(arpoja.nextInt(11));
                String juoma2 = juomat.get(arpoja.nextInt(11));
                
		if (!juoma1.equals("vettä") && !juoma2.equals("vettä")){
                	hinta += 0.5;
                }
		if (juoma1.equals(juoma2)){
         	      	ohjeet.append("kaksi lasia " + juoma1 + ".");
           	} else {
         	      	ohjeet.append("yhden lasin " + juoma1 + " ja yhden lasin " + juoma2 + ".");
            	}

                laheta(ohjeet.toString());

                luku = arpoja.nextInt(10);

                if (luku > 1){
                    hinta = hinta + 1.2;
                    laheta("Mikä onnenpäivä! Tänään syöt myös jälkiruokaa!");
                } else {
                    laheta("Voi surku! Tänään et saa jälkiruokaa. :(");
                }

                luku = arpoja.nextInt(10);

                if (luku < 4){
                    hinta = hinta + 0.19;
                    laheta("Älä ole pahvi! Käy hakemassa Gurulasta 19 sentin kahvi!");
                } else if (luku < 7){
                    hinta = hinta + 0.8;
                    laheta("Osta myös Unicafen lounaskahvi! Kun kädessäsi on Löfbergin pahvimuki, ratkeaa koodisi bugi.");
                } else {
                    laheta("Älä kuitenkaan nauti liikaa kofeiinia! Ei kahvia tänään.");
                }

                laheta("Lounaasi maksaa " + Math.floor(hinta * 100) / 100 + " euroa.");

                break;
        }
    }

    private String randomAteria(String tiedosto) {
        ArrayList<String> rivit = new ArrayList<>();

        // luodaan lukija tiedoston lukemista varten
        try (Scanner tiedostonLukija = new Scanner(Paths.get(tiedosto))) {

            // luetaan kaikki tiedoston rivit
            while (tiedostonLukija.hasNextLine()) {
                rivit.add(tiedostonLukija.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }

        return rivit.get(arpoja.nextInt(rivit.size())) + ".";
    }


    @Override
    public String getBotUsername() {
        return "/*bot username here*/";
    }

    @Override
    public String getBotToken() {
        return dotenv.get("BOT_TOKEN");
    }
}
