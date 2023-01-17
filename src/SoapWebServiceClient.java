import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.BufferedReader;

public class SoapWebServiceClient {

    public static void main(String args[]) throws Exception {

        String coord1 = "46.861";
        String coord2 = "-68.012";
        String time1 = "2023-01-16T16:1"; //2023-01-16T16%3A14
        String time2 = "2023-01-17T16:1"; //2023-01-17T16%3A14

        try {
            //String url = "https://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php";
            String url = "https://graphical.weather.gov/xml/SOAP_server/ndfdXMLclient.php?whichClient=NDFDgen&lat="
                    + coord1 + "&lon=" + coord2 + "&listLatLon=&lat1=&lon1=&lat2=&lon2=&resolutionSub=&listLat1=&listLon1=&listLat2=&listLon2=&resolutionList=&endPoint1Lat=&endPoint1Lon=&endPoint2Lat=&endPoint2Lon=&listEndPoint1Lat=&listEndPoint1Lon=&listEndPoint2Lat=&listEndPoint2Lon=&zipCodeList=&listZipCodeList=&centerPointLat=&centerPointLon=&distanceLat=&distanceLon=&resolutionSquare=&listCenterPointLat=&listCenterPointLon=&listDistanceLat=&listDistanceLon=&listResolutionSquare=&citiesLevel=&listCitiesLevel=&sector=&gmlListLatLon=&featureType=&requestedTime=&startTime=&endTime=&compType=&propertyName=&product=time-series&"
            + "begin=" + time1 + "&end=" + time2 + "&Unit=m&maxt=maxt&mint=mint&dew=dew&wspd=wspd&rh=rh&Submit=Submit";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8 ");

//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime now = LocalDateTime.now();
//            System.out.println(dtf);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String dt = sdf.format(new Date());
            //System.out.println(dt);

//            String coord1 = "46.861";
//            String coord2 = "-68.012";
//            String time1 = "2023-01-16T16:1";
//            String time2 = "2023-01-17T16:1";

//            String xml = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ndf=\"https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl\">\n" +
//                    "   <soapenv:Header/>\n" +
//                    "   <soapenv:Body>\n" +
//                    "      <ndf:NDFDgen soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
//                    "         <latitude xsi:type=\"xsd:decimal\">" + coord1 + "</latitude>\n" +
//                    "         <longitude xsi:type=\"xsd:decimal\">" + coord2 + "</longitude>\n" +
//                    "         <product xsi:type=\"dwml:productType\" xmlns:dwml=\"https://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd\">time-series</product>\n" +
//                    "         <startTime xsi:type=\"xsd:dateTime\">" + time1 + "</startTime>\n" +
//                    "         <endTime xsi:type=\"xsd:dateTime\">" + time2 + "</endTime>\n" +
//                    "         <Unit xsi:type=\"dwml:unitType\" xmlns:dwml=\"https://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd\">m</Unit>\n" +
//                    "         <weatherParameters xsi:type=\"dwml:weatherParametersType\" xmlns:dwml=\"https://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd\">\n" +
//                    "         <maxt xsi:type=\"xsd:boolean\">TRUE</maxt>\n" +
//                    "         <mint xsi:type=\"xsd:boolean\">TRUE</mint>\n" +
//                    "         <rh xsi:type=\"xsd:boolean\">TRUE</rh>\n" +
//                    "         <wspd xsi:type=\"xsd:boolean\">TRUE</wspd>\n" +
//                    "         <dew xsi:type=\"xsd:boolean\">TRUE</dew>\n" +
//                    "         </weatherParameters>\n" +
//                    "      </ndf:NDFDgen>\n" +
//                    "   </soapenv:Body>\n" +
//                    "</soapenv:Envelope>";
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
        } catch (Exception e){
            System.out.println(e);
        }
    }
}