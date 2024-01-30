import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Telefone {

    private static Long contadorIds = 0L;
    private Long id;
    private String ddd;
    private Long numero;

    private Long contatoId;

    public Long getContatoId() {
        return contatoId;
    }

    public void setContatoId(Long contatoId) {
        this.contatoId = contatoId;
    }

    public Telefone(Long id, String ddd, Long numero, Long contatoId) {

        this.contadorIds++;
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
        this.contatoId = contatoId;
    }

    public Telefone() {

        this.contadorIds++;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public void listarTelefones(List<Telefone> telefones) {
    }

    public void telefoneExiste(String dddInformado, Long telefone, Scanner scan) {

        try (BufferedReader br = new BufferedReader(new FileReader("src/telefones.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {

                String[] telefones = linha.split(";");

                if(telefones.length == 1){
                    return;
                }

                String ddd = telefones[1];
                long numero = Long.parseLong(telefones[2]);
                if (numero == telefone && ddd.equals(dddInformado)) {
                    System.out.println("Telefone já cadastrado, insira um outro número!");

                    System.out.println("Informe o DDD: ");
                    ddd = scan.next();
                    System.out.println("Informe o telefone: ");
                    numero = Long.parseLong(scan.next());
                    this.telefoneExiste(ddd, numero, scan);
                    break;
                }


            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

