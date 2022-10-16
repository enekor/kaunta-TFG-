import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/widgets.dart';

class VerContador extends StatelessWidget {
  const VerContador({super.key});

  @override
  Widget build(BuildContext context) {
    RxBool cambiarNombre = false.obs;
    RxBool cambiarDesc = false.obs;
    RxBool cambiarCount = false.obs;
    RxInt count = 1.obs;

    return Obx(
      () => Scaffold(
        backgroundColor: Temas().getBackground(),
        appBar: AppBar(
          backgroundColor: Temas().getPrimary(),
          title: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "Kaunta  ",
                style: TextStyle(color: Temas().getTextColor()),
              ),
              const SizedBox(width: 12),
              Icon(
                Icons.wifi_off_rounded,
                color: Temas().getTextColor(),
              ),
            ],
          ),
          actions: [
            IconButton(
              onPressed: () => Temas().actual.value == 1
                  ? Temas().actual.value = 0
                  : Temas().actual.value = 1,
              icon: Icon(
                Temas().actual.value == 0
                    ? Icons.dark_mode_rounded
                    : Icons.light_mode_rounded,
                color: Temas().getTextColor(),
              ),
            ),
          ],
          leading: IconButton(
            onPressed: () => Navigator.of(context).pop(),
            icon: const Icon(Icons.arrow_back_ios_new_rounded),
          ),
        ),
        body: Container(
          color: Temas().getBackground(),
          height: double.infinity,
          child: SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.all(25.0),
              child: Center(
                child: Column(
                  children: [
                    //nombre del contador
                    cambiarNombre.value == false
                        ? GestureDetector(
                            onTap: () => cambiarNombre.value = true,
                            child: Text(
                              Listado().cActual.name!.value,
                              style: TextStyle(
                                fontSize: 45,
                                color: Temas().getTextColor(),
                              ),
                            ),
                          )
                        : Column(
                            children: [
                              cTextField(
                                (valor) =>
                                    Listado().cActual.name!.value = valor,
                                "Nombre del contador",
                                Icons.abc_rounded,
                                -1,
                              ),
                              OutlinedButton(
                                onPressed: () => cambiarNombre.value = false,
                                child: const Text("Guardar"),
                              ),
                            ],
                          ),
                    const SizedBox(height: 20),
                    Image.network(Listado().cActual.image!.value),
                    const SizedBox(height: 20),
                    Text(
                      Listado().cActual.count!.value.toString(),
                      style: TextStyle(
                        fontSize: 50,
                        color: Temas().getTextColor(),
                      ),
                    ),
                    const SizedBox(height: 20),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        IconButton(
                          onPressed: () =>
                              Listado().cActual.count!.value += count.value,
                          icon: const Icon(
                            Icons.add_circle_outline_rounded,
                            color: Colors.lightGreenAccent,
                          ),
                          iconSize: 80,
                        ),
                        GestureDetector(
                          onTap: () => cambiarCount.value = !cambiarCount.value,
                          child: Text(
                            count.value.toString(),
                            style: TextStyle(
                              fontSize: 25,
                              color: Temas().getTextColor(),
                            ),
                          ),
                        ),
                        IconButton(
                          onPressed: () =>
                              Listado().cActual.count!.value -= count.value,
                          icon: const Icon(
                            Icons.remove_circle_outline_rounded,
                            color: Colors.deepOrangeAccent,
                          ),
                          iconSize: 80,
                        ),
                      ],
                    ),

                    //cambiar contador
                    cambiarContador(cambiarCount.value, (valor) {
                      try {
                        count.value = int.parse(valor);
                      } catch (e) {
                        valor = valor;
                      }
                    }),

                    //cambiar descripcion
                    cambiarDesc.value == false
                        ? GestureDetector(
                            onTap: () => cambiarDesc.value = true,
                            child: Text(
                              Listado().cActual.descrition!.value == ""
                                  ? "Click para cambiar la descripcion"
                                  : Listado().cActual.descrition!.value,
                              style: TextStyle(
                                color: Temas().getTextColor(),
                              ),
                            ),
                          )
                        : Column(
                            children: [
                              TextFormField(
                                decoration: InputDecoration(
                                  enabledBorder: UnderlineInputBorder(
                                    borderSide:
                                        BorderSide(color: Temas().getPrimary()),
                                  ),
                                  focusedBorder: UnderlineInputBorder(
                                    borderSide:
                                        BorderSide(color: Temas().getPrimary()),
                                  ),
                                ),
                                style: TextStyle(
                                  color: Temas().getTextColor(),
                                ),
                                onChanged: (valor) =>
                                    Listado().cActual.descrition!.value = valor,
                                initialValue:
                                    Listado().cActual.descrition!.value,
                                maxLines: null,
                              ),
                              OutlinedButton(
                                onPressed: () => cambiarDesc.value = false,
                                child: Text(
                                  "Guardar",
                                  style:
                                      TextStyle(color: Temas().getTextColor()),
                                ),
                              ),
                            ],
                          ),
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

Widget cambiarContador(bool cambiar, dynamic onChange) {
  if (!cambiar) {
    return const SizedBox(height: 30);
  } else {
    return Padding(
      padding: const EdgeInsets.all(25.0),
      child: Column(
        children: [
          const SizedBox(height: 20),
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(
                width: 100,
                child: TextField(
                  style: TextStyle(color: Temas().getTextColor()),
                  onChanged: onChange,
                  decoration: InputDecoration(
                      enabledBorder: OutlineInputBorder(
                        borderSide: BorderSide(
                          width: 3,
                          color: Temas().getPrimary(),
                        ),
                        borderRadius: BorderRadius.circular(25),
                      ),
                      focusedBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(25),
                        borderSide: BorderSide(
                          color: Temas().getPrimary(),
                        ),
                      )),
                ),
              ),
              const SizedBox(height: 20),
            ],
          ),
        ],
      ),
    );
  }
}
