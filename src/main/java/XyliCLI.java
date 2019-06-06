import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class XyliCLI {
    public static class Calc {
        public static double round(double value, int decimal_places) {
            double mltp = Math.pow(10, decimal_places);
            return Math.round(value*mltp)/mltp;
        }
    }

    public static class SimpleHttpRequest {
        public static String execute(String targetURL, String urlParameters, String method, String[][] headers) {
            if (method.equals("GET")) {
                targetURL += "?" + urlParameters;
            }

            HttpURLConnection connection = null;

            try {
                URL url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();

                if(headers.length > 0) {
                    for (int i = 0; i < headers.length; i++) {
                        if(headers[i].length == 2) {
                            connection.setRequestProperty(headers[i][0], headers[i][1]);
                        } else {
                            throw new Exception("executeHttpRequest(): The header size must be 2");
                        }
                    }
                }

                connection.setRequestMethod(method);
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length",
                        Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoOutput(true);

                //Send request
                if (method.equals("POST")) {
                    DataOutputStream wr = new DataOutputStream(
                            connection.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.close();
                }

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    public class IO {
        public class CLI {
            BufferedReader bufferedReader;

            CLI() {
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            }

            public String readString(String inputMessage, String errorMessage) {
                String inputValue = "";
                boolean error;
                do {
                    error = false;
                    try {
                        System.out.print(inputMessage + ": ");
                        inputValue = bufferedReader.readLine();
                    } catch (IOException e) {
                        System.out.println(errorMessage);
                        error = true;
                    }
                } while(error);
                return inputValue;
            }

            public int readInt(String inputMessage, String errorMessage) {
                int inputValue = -1;
                boolean error;
                do {
                    error = false;
                    try {
                        System.out.print(inputMessage + ": ");
                        inputValue = Integer.parseInt(bufferedReader.readLine());
                    } catch (IOException e) {
                        System.out.println(errorMessage);
                        error = true;
                    }
                } while(error);
                return inputValue;
            }

            public double readDouble(String inputMessage, String errorMessage) {
                double inputValue = -1;
                boolean error;
                do {
                    error = false;
                    try {
                        System.out.print(inputMessage + ": ");
                        inputValue = Double.parseDouble(bufferedReader.readLine());
                    } catch (IOException e) {
                        System.out.println(errorMessage);
                        error = true;
                    }
                } while(error);
                return inputValue;
            }

        }
    }

}
