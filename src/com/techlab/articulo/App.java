package com.techlab.articulo;

import com.techlab.articulo.model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Articulo> articulos = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();
        precargarCategorias(categorias);

        int opcion;
        do {
            System.out.println("======================================================");
            System.out.println("SISTEMA DE ARTÍCULOS - HERENCIA Y TO_STRING");
            System.out.println("======================================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Listar categorías");
            System.out.println("0 - Salir");
            System.out.println("======================================================");
            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch(opcion) {
                case 1: ingresarArticulo(scanner, articulos, categorias); break;
                case 2: listarArticulos(articulos); break;
                case 3: consultarArticulo(scanner, articulos); break;
                case 4: modificarArticulo(scanner, articulos, categorias); break;
                case 5: eliminarArticulo(scanner, articulos); break;
                case 6: listarCategorias(categorias); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while(opcion != 0);

        scanner.close();
    }

    // Precarga de categorías
    public static void precargarCategorias(ArrayList<Categoria> categorias) {
        categorias.add(new Categoria(1, "Electrónica", "Productos tecnológicos y electrónicos"));
        categorias.add(new Categoria(2, "Periféricos", "Accesorios para computadora"));
        categorias.add(new Categoria(3, "Alimentos", "Productos alimenticios"));
        categorias.add(new Categoria(4, "Limpieza", "Artículos de limpieza del hogar"));
    }

    // Ingresar artículo
    public static void ingresarArticulo(Scanner scanner, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias) {
        System.out.println("--- INGRESAR ARTÍCULO ---");
        int tipo;
        do {
            tipo = leerEntero(scanner, "1 - Artículo electrónico\n2 - Artículo alimenticio\nSeleccione el tipo: ");
        } while(tipo != 1 && tipo != 2);

        int codigo = leerEntero(scanner, "Ingrese el código: ");
        if(buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio: ");

        listarCategorias(categorias);
        Categoria categoria = pedirCategoriaExistente(scanner, categorias);

        Articulo articulo;
        if(tipo == 1) {
            int garantia = leerEnteroNoNegativo(scanner, "Ingrese la garantía en meses: ");
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantia);
        } else {
            int dias = leerEnteroNoNegativo(scanner, "Ingrese días para vencimiento: ");
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, dias);
        }

        articulos.add(articulo);
        System.out.println("Artículo ingresado correctamente:\n" + articulo);
    }

    // Listar artículos
    public static void listarArticulos(ArrayList<Articulo> articulos) {
        if(articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
        } else {
            for(Articulo a : articulos) {
                System.out.println(a);
            }
        }
    }

    // Consultar artículo
    public static void consultarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {
        if(articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }
        int codigo = leerEntero(scanner, "Ingrese el código a consultar: ");
        Articulo a = buscarArticuloPorCodigo(articulos, codigo);
        if(a == null) {
            System.out.println("El artículo no existe.");
        } else {
            System.out.println(a);
            System.out.println("Detalle específico: " + a.getDetalleEspecifico());
        }
    }

    // Modificar artículo
    public static void modificarArticulo(Scanner scanner, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias) {
        if(articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }
        int codigo = leerEntero(scanner, "Ingrese el código a modificar: ");
        Articulo a = buscarArticuloPorCodigo(articulos, codigo);
        if(a == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        a.setNombre(leerTextoNoVacio(scanner, "Nuevo nombre: "));
        a.setPrecio(leerDoubleNoNegativo(scanner, "Nuevo precio: "));
        listarCategorias(categorias);
        a.setCategoria(pedirCategoriaExistente(scanner, categorias));

        if(a instanceof ArticuloElectronico) {
            ((ArticuloElectronico)a).setGarantiaMeses(leerEnteroNoNegativo(scanner, "Nueva garantía en meses: "));
        } else if(a instanceof ArticuloAlimenticio) {
            ((ArticuloAlimenticio)a).setDiasParaVencimiento(leerEnteroNoNegativo(scanner, "Nuevos días para vencimiento: "));
        }

        System.out.println("Artículo modificado correctamente.");
    }

    // Eliminar artículo
    public static void eliminarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {
        if(articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }
        int codigo = leerEntero(scanner, "Ingrese el código a eliminar: ");
        Articulo a = buscarArticuloPorCodigo(articulos, codigo);
        if(a == null) {
            System.out.println("El artículo no existe.");
        } else {
            articulos.remove(a);
            System.out.println("Artículo eliminado correctamente.");
        }
    }

    // Listar categorías
    public static void listarCategorias(ArrayList<Categoria> categorias) {
        for(Categoria c : categorias) {
            System.out.println(c);
        }
    }

    // Métodos auxiliares
    public static Articulo buscarArticuloPorCodigo(ArrayList<Articulo> articulos, int codigo) {
        for(Articulo a : articulos) {
            if(a.getCodigo() == codigo) return a;
        }
        return null;
    }

    public static Categoria buscarCategoriaPorCodigo(ArrayList<Categoria> categorias, int codigo) {
        for(Categoria c : categorias) {
            if(c.getCodigo() == codigo) return c;
        }
        return null;
    }

    public static Categoria pedirCategoriaExistente(Scanner scanner, ArrayList<Categoria> categorias) {
        Categoria c;
        do {
            int codigo = leerEntero(scanner, "Ingrese código de categoría: ");
            c = buscarCategoriaPorCodigo(categorias, codigo);
            if(c == null) System.out.println("Categoría inexistente, intente de nuevo.");
        } while(c == null);
        return c;
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while(true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Formato inválido, intente de nuevo.");
            }
        }
    }

    public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        int valor;
        do {
            valor = leerEntero(scanner, mensaje);
            if(valor < 0) System.out.println("Debe ser un número no negativo.");
        } while(valor < 0);
        return valor;
    }

    public static double leerDoubleNoNegativo(Scanner scanner, String mensaje) {
        while(true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());
                if(valor >= 0 && Double.isFinite(valor)) {
                    return valor;
                } else {
                    System.out.println("Debe ser un número válido y no negativo.");
                }
            } catch(NumberFormatException e) {
                System.out.println("Formato inválido, intente de nuevo.");
            }
        }
    }

    public static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if(texto.isEmpty()) {
                System.out.println("El texto no puede estar vacío.");
            }
        } while(texto.isEmpty());
        return texto;
    }
}
