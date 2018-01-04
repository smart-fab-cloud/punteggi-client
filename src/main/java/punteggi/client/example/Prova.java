package punteggi.client.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Prova {
    
    public static void main(String[] args) {
        
        // Creazione di un client "cli"
        Client cli = ClientBuilder.newClient();
        
        // Connessione di "cli" alla risorsa "punteggi"
        WebTarget punteggi = cli.target("http://localhost:56476/punteggi");
        
        // Aggiunta di un nuovo punteggio
        Response rPos = punteggi.queryParam("giocatore", "jacopo")
                            .queryParam("punteggio", "345")
                            .request()
                            .post(Entity.entity("", MediaType.TEXT_PLAIN));
        
        // Lettura punteggio inserito 
        Response rGet = punteggi.path("jacopo")
                            .request(MediaType.APPLICATION_JSON)
                            .get();
        System.out.println(rGet.readEntity(String.class));
        
        // Aggiornamento punteggio
        Response rPut = punteggi.path("jacopo")
                            .queryParam("punteggio", "895")
                            .request()
                            .put(Entity.entity("", MediaType.TEXT_PLAIN));
        
        // Lettura punteggio aggiornato 
        Response rGet2 = punteggi.path("jacopo")
                            .request(MediaType.APPLICATION_JSON)
                            .get();
        System.out.println(rGet2.readEntity(String.class));
        
        // Eliminazione punteggio
        Response rDel = punteggi.path("jacopo")
                            .request()
                            .delete();
    }
}

