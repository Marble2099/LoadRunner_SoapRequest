import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SoapWebServiceClient {

    public static void main(String args[]) throws Exception {
        //случайным образом выбирается город из файла для запроса погоды

        InputStream is = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/List.csv")));

            //Считываем файл
            List<String> list = new ArrayList<String>();
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }

            //Берем случайное значение
            SecureRandom random = new SecureRandom();
            int row = random.nextInt(list.size());
            String citi = list.get(row);
            String[] mas = citi.split(";");

//            System.out.println("Random selection is:\n");
//            System.out.println(list.get(row));
//            System.out.println(mas[0] + mas[1] + mas[2]);

            // Берем текущее время и время на минуту раньше
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dt = new Date();
            System.out.println(sdf.format(dt));

            // Конвертируем Date в Calendar
            Calendar can = Calendar.getInstance();
            Calendar can1 = Calendar.getInstance();
            can.setTime(dt);

            // Проводим вычитание одной минуты из текущего времени
            //can.add(Calendar.DATE, -1);
            can.add(Calendar.MINUTE, -1);

            // Конвертиурем calendar обратно в Date
            Date currentDateMinusOne = can.getTime();

            System.out.println(sdf.format(currentDateMinusOne));

//            String coord1 = "34.52"; //mas[1]
//            String coord2 = "82.58"; //mas[2]
            String time2 = sdf.format(dt);
            String time1 = sdf.format(currentDateMinusOne);

        try {
            //String url = "https://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php";
            String url = "https://graphical.weather.gov/xml/SOAP_server/ndfdXMLclient.php?whichClient=NDFDgen&lat="
                    + mas[1] + "&lon=" + mas[2] + "&listLatLon=&lat1=&lon1=&lat2=&lon2=&resolutionSub=&listLat1=&listLon1=&listLat2=&listLon2=&resolutionList=&endPoint1Lat=&endPoint1Lon=&endPoint2Lat=&endPoint2Lon=&listEndPoint1Lat=&listEndPoint1Lon=&listEndPoint2Lat=&listEndPoint2Lon=&zipCodeList=&listZipCodeList=&centerPointLat=&centerPointLon=&distanceLat=&distanceLon=&resolutionSquare=&listCenterPointLat=&listCenterPointLon=&listDistanceLat=&listDistanceLon=&listResolutionSquare=&citiesLevel=&listCitiesLevel=&sector=&gmlListLatLon=&featureType=&requestedTime=&startTime=&endTime=&compType=&propertyName=&product=time-series&"
            + "begin=" + time1 + "&end=" + time2 + "&Unit=m&maxt=maxt&mint=mint&dew=dew&wspd=wspd&rh=rh&Submit=Submit";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8 ");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(url);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            System.out.println(responseStatus);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response.toString());

            // выводим максимальную и минимальную температуру воздуха за текущий  день (в градусах Фаренгейта или Цельсия),
            // влажность воздуха, скорость ветра и температуру точки росы
            String input = response.toString();
            Pattern pattern = Pattern.compile("<name>([^<]+)<\\/name>\\s*<value>([\\d-]{1,3})<\\/value>");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find() == true) {
                System.out.println(mas[0]);
                System.out.println(matcher.group(1) + " " + matcher.group(2));
            while(matcher.find())
                System.out.println(matcher.group(1) + " " + matcher.group(2));
            } else {
                System.out.println("FAIL"); // когда сервис не найдет город или страну, запрос переходит в статус «FAIL»
            }
        } catch (Exception e){
            System.out.println(e);
        }

        } finally {
            if (is != null) {
                is.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}