package com.example.patri.appbolaoprojeto.WS;

import com.example.patri.appbolaoprojeto.Entity.Classificacao;
import com.example.patri.appbolaoprojeto.Entity.Equipe;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.example.patri.appbolaoprojeto.WS.WSConstantes.NAMESPACE;
import static com.example.patri.appbolaoprojeto.WS.WSConstantes.SOAP_ACTION;
import static com.example.patri.appbolaoprojeto.WS.WSConstantes.URL;
import static com.example.patri.appbolaoprojeto.WS.WSConstantes.URL_LIST_CLASSIFICACAO;
import static com.example.patri.appbolaoprojeto.WS.WSConstantes.URL_LIST_EQUIPE;

/**
 * Created by Bruno on 29/03/2018.
 */

public class WSGetClassificacao {

    public static void getClassificacaoList(){
        try  {
            SoapObject request = new SoapObject(NAMESPACE,URL_LIST_CLASSIFICACAO);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION + URL_LIST_CLASSIFICACAO, envelope);
                SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                final String dados = resultsRequestSOAP.toString();
                JSONArray jsonArray = new JSONArray(dados);
                for (int i=0; i < jsonArray.length(); i++) {
                    Classificacao c = new Gson().fromJson(jsonArray.get(i).toString(), Classificacao.class); //banco
                    c.getDerrota().save();
                    c.getEmpate().save();
                    c.getJogos().save();
                    c.getPontosGanhos().save();
                    c.getVitoria();
                    c.save();
                };
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
