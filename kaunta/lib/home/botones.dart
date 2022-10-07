import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/con_conexion.dart';
import 'package:kaunta/home/sin_conexion.dart';
import 'package:kaunta/themes/temas.dart';

class Botones extends StatelessWidget {
  const Botones({super.key});

  @override
  Widget build(BuildContext context) {
    return Obx(
      () => Scaffold(
        floatingActionButton: FloatingActionButton(
          onPressed: () => cambiarTema(),
          child: Icon(Temas().actual.value == 0
              ? Icons.dark_mode_rounded
              : Icons.light_mode_rounded),
        ),
        body: Container(
          color: Temas().getBackground(),
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Padding(
                  padding: const EdgeInsets.only(bottom: 50),
                  child: Column(
                    children: [
                      IconButton(
                        onPressed: () =>
                            abrirPagina(const SinConexion(), context),
                        icon: const Icon(
                          Icons.wifi_off_rounded,
                          color: Colors.redAccent,
                        ),
                      ),
                      const Text(
                        "Sin conexion",
                        style: TextStyle(color: Colors.redAccent),
                      )
                    ],
                  ),
                ),
                Column(
                  children: [
                    IconButton(
                      onPressed: () => abrirPagina(ConConexion(), context),
                      icon: const Icon(
                        Icons.wifi_rounded,
                        color: Colors.greenAccent,
                      ),
                    ),
                    const Text(
                      "Conectado",
                      style: TextStyle(color: Colors.greenAccent),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void abrirPagina(Widget pagina, BuildContext context) {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => pagina,
      ),
    );
  }

  void cambiarTema() {
    if (Temas().actual.value == 1) {
      Temas().actual.value = 0;
    } else {
      Temas().actual.value = 1;
    }
  }
}
