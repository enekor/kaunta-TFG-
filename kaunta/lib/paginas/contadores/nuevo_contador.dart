import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/json.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/snackers.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget nuevoContador(BuildContext context) {
  Contador nuevoContador = Contador(
      name: "".obs,
      count: 0.obs,
      image:
          "https://www.familiasnumerosascv.org/wp-content/uploads/2015/05/icono-camara.png"
              .obs);

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
              Image.network(nuevoContador.image!.value),
              const SizedBox(height: 15),
              cTextField((valor) => nuevoContador.name!.value = valor,
                  "Nombre del contador", Icons.abc, -1),
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
                1,
              ),
              const SizedBox(height: 15),
              cTextField(
                (String valor) {
                  if (valor.endsWith(".png") ||
                      valor.endsWith(".jpg") ||
                      valor.endsWith(".jpeg")) {
                    nuevoContador.image!.value = valor;
                    Temas().cImagenValido.value = true;
                  } else if (valor == "") {
                    nuevoContador.image!.value =
                        "https://www.familiasnumerosascv.org/wp-content/uploads/2015/05/icono-camara.png";
                    Temas().cImagenValido.value = true;
                  } else {
                    Temas().cImagenValido.value = false;
                  }
                },
                "Imagen del contador ( .png | .jpg | .jpeg)",
                Icons.link_rounded,
                2,
              ),
              const SizedBox(height: 15),
              OutlinedButton(
                onPressed: () {
                  bool guardable = false;

                  if (nuevoContador.name!.value != "" &&
                      nuevoContador.name != null &&
                      Temas().cContadorValido.value &&
                      Temas().cImagenValido.value) {
                    guardable = true;
                  } else {
                    var snack = Snacker().failSnacker();
                    showSnack(snack, context);
                  }

                  if (guardable) {
                    guardarContador(nuevoContador);
                    var snack = Snacker().succedSnacker();
                    showSnack(snack, context);
                    saveCounters();
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

  Listado().gActual.counters!.add(nuevoContador);
}
