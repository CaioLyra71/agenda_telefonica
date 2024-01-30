import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contato {

    private static Long contadorIds = 0L;
    private  Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    private String file;


    public Contato() {
        this.telefones = new ArrayList<>();
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobreNome() {
        return this.sobreNome;
    }

    public List<Telefone> getTelefones() {
        return this.telefones;
    }

    public  void setContadorIds(){
        ++contadorIds;
    }

    public void setId() {
         id = contadorIds;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }



    public Long localizarId() {

        try (BufferedReader br = new BufferedReader(new FileReader("src/agenda.txt"))) {
            String linha;
            Long id = 0L;
            while ((linha = br.readLine()) != null) {

                String[] dadosContato = linha.split(";");

                if (dadosContato.length == 1) {

                    return 0L;
                }
                id = Long.parseLong(dadosContato[0]);


            }

            return id;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public Long incrementarId() {
        this.id = 0L;
        long idEncontrado = this.localizarId();
        this.id = idEncontrado + 1;

        System.out.println( this.id);
        return 1L;
    }







    public Contato buscarId(List<Contato> listaContatos, Long id) {

        for (Contato contato : listaContatos) {
            if (id.equals(contato.getId())) {
               return contato;
            }
        }
            return null;
    }

    public void removerContato(List<Contato> listaContatos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o id do contato que deseja remover:");
        long id = scanner.nextLong();

        listaContatos.removeIf(contato -> contato.getId() == id);
        System.out.println("Contato removido com sucesso!");




    }




}

