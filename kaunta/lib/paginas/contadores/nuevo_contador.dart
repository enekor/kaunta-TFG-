import 'package:flutter/material.dart';
import 'package:get/get.dart';
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
      color: Temas().getBackground(),
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
              cTextField(
                (valor) => nuevoContador.name!.value = valor,
                "Nombre del contador",
                Icons.abc,
              ),
              const SizedBox(height: 15),
              cTextField(
                (valor) {
                  try {
                    int.parse(valor);
                    nuevoContador.count!.value = int.parse(valor);
                  } catch (e) {
                    var snack = Snacker().failSnacker();
                    showSnack(snack, context);
                  }
                },
                "Contador incial",
                Icons.numbers,
              ),
              const SizedBox(height: 15),
              cTextField((String valor) {
                if (valor.endsWith(".png") ||
                    valor.endsWith(".jpg") ||
                    valor.endsWith(".jpeg")) {
                  nuevoContador.image!.value = valor;
                } else {
                  var snack = Snacker().failSnacker();
                  showSnack(snack, context);
                }
              }, "Imagen del contador ( .png | .jpg | .jpeg)",
                  Icons.link_rounded),
              const SizedBox(height: 15),
              OutlinedButton(
                onPressed: () {
                  bool guardable = false;

                  if (nuevoContador.name!.value != "" &&
                      nuevoContador.name != null &&
                      nuevoContador.count != null) {
                    guardable = true;
                  } else {
                    var snack = Snacker().failSnacker();
                    showSnack(snack, context);
                  }

                  if (guardable) {
                    guardarContador(nuevoContador);
                    var snack = Snacker().succedSnacker();
                    showSnack(snack, context);
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
