import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:image_picker/image_picker.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/json.dart';
import 'package:kaunta/model/crear_contador.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/widgets/snackers.dart';
import 'package:kaunta/widgets/widgets.dart';

final ImagePicker _imgPicker = ImagePicker();
RxString imagen =
    "https://www.familiasnumerosascv.org/wp-content/uploads/2015/05/icono-camara.png"
        .obs;

Widget nuevoContador(BuildContext context) {
  Contador nuevoContador = Contador(name: "".obs, count: 0.obs, image: imagen);

  return Obx(
    () => Container(
      margin: const EdgeInsets.all(25),
      child: Center(
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              Text(
                "Nuevo contador",
                style: TextStyle(color: Temas().getTextColor(), fontSize: 25),
              ),
              const SizedBox(height: 15),
              Globales().conectado
                  ? Image.network(nuevoContador.image!.value)
                  : Column(
                      children: [
                        NoNetworkImage(),
                        Text(
                          "Se usara la imagen por defecto al estar sin conexion",
                          style: TextStyle(
                            color: Temas().getTextColor(),
                            fontSize: 25,
                          ),
                        ),
                      ],
                    ),
              const SizedBox(height: 15),
              cTextField((valor) => nuevoContador.name!.value = valor,
                  "Nombre del contador", Icons.abc, true.obs),
              const SizedBox(height: 15),
              cTextField(
                (valor) {
                  try {
                    if (valor == "") {
                      nuevoContador.count!.value = 0;
                    } else {
                      nuevoContador.count!.value = int.parse(valor);
                    }
                    Temas().cContadorValido.value = true;
                  } catch (e) {
                    Temas().cContadorValido.value = false;
                  }
                },
                "Contador incial",
                Icons.numbers,
                Temas().cContadorValido,
              ),
              const SizedBox(height: 15),
              Globales().conectado
                  ? ElevatedButton(
                      onPressed: elegirImagen,
                      child: Text(
                        "Seleccionar imagen",
                        style: TextStyle(
                          color: Temas().getButtonTextColor(),
                          fontSize: 16,
                        ),
                      ),
                    )
                  : Container(),
              const SizedBox(height: 15),
              OutlinedButton(
                onPressed: () async {
                  bool guardable = false;
                  bool a = Temas().cContadorValido.value;

                  if (nuevoContador.name!.value != "" &&
                      nuevoContador.name != null &&
                      Temas().cContadorValido.value) {
                    guardable = true;
                  } else {
                    var snack = Snacker().failSnacker();
                    showSnack(snack, context);
                  }

                  if (guardable) {
                    if (Globales().conectado) {
                      CrearContador contador = CrearContador(
                        name: nuevoContador.name!.value,
                        count: nuevoContador.count!.value,
                        description: "",
                        group: Globales().grupo,
                        image: nuevoContador.image!.value,
                      );

                      int subida = await ApiCall().createContador(contador);

                      var snack;
                      if (subida == 200) {
                        snack = Snacker().succedSnacker();
                      } else {
                        snack = Snacker().failSnacker();
                      }

                      showSnack(snack, context);
                      loadCounters();
                    } else {
                      guardarContador(nuevoContador);
                      var snack = Snacker().succedSnacker();
                      showSnack(snack, context);
                    }
                  }
                },
                child: const Text("Guardar"),
              ),
            ],
          ),
        ),
      ),
    ),
  );
}

guardarContador(Contador nuevoContador) {
  nuevoContador.active = true.obs;
  nuevoContador.descrition = "".obs;
  nuevoContador.id = -1.obs;
  Listado()
      .usuario
      .value
      .grupos![Listado().gActual]
      .counters!
      .add(nuevoContador);
  int u = Listado().usuario.value.grupos![Listado().gActual].counters!.length;
  saveCounters();
}

elegirImagen() async {
  final XFile? fileImagen =
      await _imgPicker.pickImage(source: ImageSource.gallery);
  File file = File(fileImagen!.path);

  String resp = await ApiCall().uploadImage(file);
  imagen.value = resp;
}
