import 'package:flutter/material.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:get/get.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/widgets.dart';

void guardarGrupo(String nombre) {
  Grupo g =
      Grupo(nombre: nombre.obs, activo: true.obs, counters: <Contador>[].obs);

  Listado().grupos.add(g);
}

Widget nuevoGrupo() {
  RxString nombreNuevoGrupo = "".obs;

  return Obx(
    () => SingleChildScrollView(
      child: Container(
        color: Temas().getBackground(),
        child: Center(
          child: Padding(
            padding: const EdgeInsets.all(80.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                  padding: const EdgeInsets.only(top: 25),
                  child: Icon(
                    Icons.add_circle_rounded,
                    size: 200,
                    color: Temas().getSecondary(),
                  ),
                ),
                Container(
                  padding: const EdgeInsets.only(top: 205),
                  child: cTextField(
                    (valor) => nombreNuevoGrupo.value = valor,
                    "Nombre del grupo",
                    Icons.abc,
                  ),
                ),
                Container(
                  padding: const EdgeInsets.only(top: 25),
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      primary: Temas().getSecondary(),
                    ),
                    onPressed: () {
                      guardarGrupo(nombreNuevoGrupo.value);
                    },
                    child: const Text("Guardar"),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    ),
  );
}
