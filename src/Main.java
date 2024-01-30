import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static List<Contato> listaContatos = new ArrayList<>();

    public static void main(String[] args) {
        mostrarAgenda();
        lerListaDeContatos();
        iniciarMenu();


    }

    public static void mostrarAgenda() {
        System.out.println("##################");
        System.out.println("##### AGENDA #####");
        System.out.println("##################");
    }


    public static void iniciarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        do {


            Contato contato = new Contato();
            listarContatos();
            mostrarMenu();

            System.out.print("Digite a opção desejada: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    adicionarContato(contato);
                    escreverNoArquivoContato();
                    escreverNoArquivoTelefone(listaContatos);
                }
                case 2 -> {
                    contato.removerContato(listaContatos);
                    escreverNoArquivoContato();
                    escreverNoArquivoTelefone(listaContatos);
                }
                //case 3 -> contato.editarContato();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção invalida. Tente novamente!");
            }

        } while (opcao != 4);


        iniciarMenu();
    }


    public static void mostrarMenu() {
        System.out.println("\n>>>>Menu <<<<");
        System.out.println(" 1 - Adicionar Contato");
        System.out.println(" 2 - Remover Contato");
        System.out.println(" 3 - Editar Contato");
        System.out.println(" 4 - Sair");

    }

    public static void listarContatos() {

        System.out.println("\n>>>> Contatos <<<< ");

        Contato contato = new Contato();


        for (Contato informacaoContato : listaContatos) {

            System.out.println(informacaoContato.getId() + "|" + informacaoContato.getNome() + " " + informacaoContato.getSobreNome());

        }


    }

    public static void lerListaDeContatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/agenda.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {

                String[] dadosContato = linha.split(";");


                long id = Long.parseLong(dadosContato[0]);
                String nome = dadosContato[1];
                String sobreNome = dadosContato[2];

                Contato novoContato = new Contato();

                novoContato.setId();
                novoContato.setNome(nome);
                novoContato.setSobreNome(sobreNome);

                listaContatos.add(novoContato);


            }
            listarTelefones(listaContatos);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escreverNoArquivoContato() {
        String fileName = "src/agenda.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contato contato : listaContatos) {


                writer.write(contato.getId() + ";" + contato.getNome() + ";" + contato.getSobreNome() + ";");
                if (new File("src/contato.txt").length() > 0) {
                    writer.newLine();
                }
                writer.newLine();

            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + fileName + ": " + e.getMessage());
        }
    }

    public static void listarTelefones(List<Contato> listaContatos) {


        try (BufferedReader br = new BufferedReader(new FileReader("src/telefones.txt"))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                String array[] = linha.split(";");
                Telefone tel = new Telefone(Long.parseLong(array[0]), array[1], Long.parseLong(array[2]), Long.parseLong(array[3]));

                for (Contato contato : listaContatos) {
                    if (contato.getId() == tel.getContatoId()) {
                        contato.getTelefones().add(tel);
                    }
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void escreverNoArquivoTelefone(List<Contato> contatos) {
        String fileName = "src/telefones.txt";


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(Contato contato : contatos){
                List <Telefone> telefones = contato.getTelefones();

            for (Telefone telefoneT : telefones) {


                writer.write(telefoneT.getContatoId() + ";" + telefoneT.getDdd() + ";" + telefoneT.getNumero());

                writer.newLine();
            }

            }

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + fileName + ": " + e.getMessage());
        }
    }

    public static void adicionarContato(Contato contato) {


        Scanner scan = new Scanner(System.in);

        System.out.println("Informe o nome do contato: ");
        String nome = scan.next();
        contato.setNome(nome);
        System.out.println("Informe o sobrenome do contato: ");
        String sobreNome = scan.next();
        contato.setSobreNome(sobreNome);



        List<Telefone> telefones = new ArrayList<Telefone>();
        contato.setTelefones(telefones);
        Telefone t = new Telefone();
        t = adicionarTelefone();
        contato.setContadorIds();
        contato.setId();
        //t.setId(telefones.getId());
        t.setContatoId(contato.getId());
        contato.getTelefones().add(t);

        int continuar = -1;


        do {
            System.out.println("Deseja adicionar um novo telefone?( 0 - Não, 1-Sim ) ");
            continuar = Integer.parseInt(scan.next());

            if (continuar == 1) {

                t = adicionarTelefone();
                t.setContatoId(contato.getId());
                contato.getTelefones().add(t);
            }

        } while (continuar != 0);

        listaContatos.add(contato);


        System.out.println("Deseja adicionar um novo contato?( 0 - Não, 1-Sim ) ");
        continuar = Integer.parseInt(scan.next());

        if (continuar == 1) {
            Contato novoContato = new Contato();
            adicionarContato(novoContato);
        }


        return;


    }

    public static Telefone  adicionarTelefone() {
        Scanner scan = new Scanner(System.in);
        Telefone telefone = new Telefone();

        System.out.println("Informe o DDD: ");
        String ddd = scan.next();
        System.out.println("Informe o telefone: ");
        Long numeroTelefone = Long.parseLong(scan.next());

        telefone.telefoneExiste(ddd, numeroTelefone, scan);


        telefone.setDdd(ddd);
        telefone.setNumero(numeroTelefone);

        return telefone;


    }

}

