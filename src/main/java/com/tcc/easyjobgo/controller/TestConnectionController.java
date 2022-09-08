package com.tcc.easyjobgo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tcc.easyjobgo.database.DBConnection;

import java.sql.Connection;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class TestConnectionController {
    
    @GetMapping(value="/")
    public String getStart() {

        Connection cnn = DBConnection.startConnection();
        DBConnection.closeConnection(cnn);

        return "Conexao com o Banco de Dados Efetuada com sucesso!";
    }
}
