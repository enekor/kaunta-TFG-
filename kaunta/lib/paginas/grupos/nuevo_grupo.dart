import 'dart:math';

import 'package:flutter/material.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/json.dart';
import 'package:kaunta/model/crear_grupo.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:get/get.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/widgets/snackers.dart';
import 'package:kaunta/widgets/widgets.dart';

void guardarGrupo(String nombre, BuildContext context) async {
  if (Globales().conectado) {
    CrearGrupo g =
        CrearGrupo(id: -1, nombre: nombre, user: Listado().usuario.value.id);

    int codigo = await ApiCall().createGroup(g);

    saveCounters();

    var snack;
    if (codigo == 200) {
      snack = Snacker().succedSnacker();
    } else {
      snack = Snacker().failSnacker();
    }
    showSnack(snack, context);
  } else {
    Grupo g = Grupo(
        id: Random().nextInt(200),
        nombre: nombre.obs,
        activo: true.obs,
        counters: <Contador>[].obs);

    Listado().usuario.value.grupos!.add(g);
    saveCounters();
    var snack = Snacker().succedSnacker();
    showSnack(snack, context);
  }
}

Widget nuevoGrupo(BuildContext context) {
  RxString nombreNuevoGrupo = "".obs;

  return Obx(
    () => SingleChildScrollView(
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
                  color: Temas().getPrimary(),
                ),
              ),
              Container(
                padding: const EdgeInsets.only(top: 205),
                child: cTextField(
                  (valor) => nombreNuevoGrupo.value = valor,
                  "Nombre del grupo",
                  Icons.abc,
                  true.obs,
                ),
              ),
              Container(
                padding: const EdgeInsets.only(top: 25),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Temas().getSecondary(),
                  ),
                  onPressed: () {
                    if (nombreNuevoGrupo.value != "") {
                      guardarGrupo(nombreNuevoGrupo.value, context);
                    } else {
                      var snack = Snacker().failSnacker();
                      showSnack(snack, context);
                    }
                  },
                  child: const Text("Guardar"),
                ),
              ),
            ],
          ),
        ),
      ),
    ),
  );
}
