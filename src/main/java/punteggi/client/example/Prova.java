package punteggi.client.example;

import java.net.ConnectException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Prova {
    
    public static void main(String[] args) {
        
        // Creazione di un client "cli"
        Client cli = ClientBuilder.newClient();
        
        // Connessione di "cli" alla risorsa "punteggi"
        WebTarget punteggi = cli.target("http://localhost:56476/punteggi");
        
        // Aggiunta di un nuovo punteggio
        try {
            Response rPos = punteggi.queryParam("giocatore", "jacopo")
                            .queryParam("punteggio", "345")
                            .request()
                            .post(Entity.entity("", MediaType.TEXT_PLAIN));
            if(rPos.getStatus() != Status.CREATED.getStatusCode()) {
                System.err.println("POST: " + rPos.getStatusInfo());
                return;
            }
        } catch (ProcessingException e) {
            if(e.getCause() instanceof ConnectException)
                System.err.println("POST: Il servizio punteggi non e' online.");
            else throw e;
        } 
        
        // Lettura punteggio inserito 
        try {
            Response rGet = punteggi.path("jacopo")
                            .request(MediaType.APPLICATION_JSON)
                            .get();
            if (rGet.getStatus() == Status.OK.getStatusCode())
                System.out.println(rGet.readEntity(String.class));
            else {
                // Nel caso generale, la gestione puo' essere piu' "furba" di
                // un semplice log come in questo esempio (p.e., prevedendo
                // meccanismi o suggerimenti all'utente per evitare che il
                // problema si ripresenti).
                System.err.println("GET1: " + rGet.getStatusInfo());
                return;
            }
        } catch (ProcessingException e) {
            if(e.getCause() instanceof ConnectException)
                // Nel caso generale, potrebbe essere utilizzato un valore
                // predefinito (come entity della risposta ricevuta da servizi
                // che al momento non sono online).
                System.err.println("GET1: Il servizio punteggi non e' online");
            else throw e;
        }
        
        // Aggiornamento punteggio
        try {
            Response rPut = punteggi.path("jacopo")
                            .queryParam("punteggio", "895")
                            .request()
                            .put(Entity.entity("", MediaType.TEXT_PLAIN));
            if(rPut.getStatus() != Status.OK.getStatusCode()) {
                System.err.println("PUT: " + rPut.getStatusInfo());
                return;
            }
        } catch (ProcessingException e) {
            if(e.getCause() instanceof ConnectException)
                System.err.println("PUT: Il servizio punteggi non e' online");
            else throw e;
        }
        
        // Lettura punteggio aggiornato 
        try {
            Response rGet2 = punteggi.path("jacopo")
                            .request(MediaType.APPLICATION_JSON)
                            .get();
            if(rGet2.getStatus() == Status.OK.getStatusCode())
                System.out.println(rGet2.readEntity(String.class));
            else {
                System.err.println("GET2: + " + rGet2.getStatusInfo());
                return;
            }
        }  catch (ProcessingException e) {
            if(e.getCause() instanceof ConnectException)
                System.err.println("GET2: Il servizio punteggi non e' online");
            else throw e;
        }
        
        // Eliminazione punteggio
        try {
            Response rDel = punteggi.path("jacopo")
                            .request()
                            .delete();
            if(rDel.getStatus() != Status.OK.getStatusCode()) {
                System.err.println("DELETE: " + rDel.getStatusInfo());
                return;
            }
                
        }  catch (ProcessingException e) {
            if(e.getCause() instanceof ConnectException)
                System.err.println("DELETE: Il servizio punteggi non e' online");
            else throw e;
        }
        
        // Lettura punteggio eliminato
        try {
            Response rGet3 = punteggi.path("jacopo")
                            .request(MediaType.APPLICATION_JSON)
                            .get();
            if(rGet3.getStatus() == Status.OK.getStatusCode())
                System.out.println(rGet3.readEntity(String.class));
            else {
                System.err.println("GET3: " + rGet3.getStatusInfo());
                return;
            }
        } catch (ProcessingException e) {
            if(e.getCause() instanceof ConnectException)
                System.err.println("GET3: Il servizio punteggi non e' online");
            else throw e;
        }
    }
}

