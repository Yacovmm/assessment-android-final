package com.yacov.assessmentfundamentoandroid.model;

/**
 * Created by YacovR on 12-Dec-17.
 */

public class User {

    String name,email, senha, cpf;

    public User(String name, String email, String senha, String cpf) {
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
