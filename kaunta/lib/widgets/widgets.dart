import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';

Widget cTextField(dynamic onChange, String label, IconData icono) => TextField(
    style: TextStyle(color: Temas().getTextColor()),
    decoration: InputDecoration(
      labelStyle: TextStyle(color: Temas().getTextColor()),
      suffixIcon: Icon(icono, color: Colors.purple),
      labelText: label,
      enabledBorder: OutlineInputBorder(
        borderRadius: BorderRadius.circular(16.0),
        borderSide: BorderSide(color: Temas().getSecondary()),
      ),
      focusedBorder: OutlineInputBorder(
        borderSide: BorderSide(color: Temas().getSecondary()),
      ),
    ),
    onChanged: onChange);

Widget cGroupListItem(Grupo g, int index) => Obx(
      () => Card(
        color: Temas().getSecondary(),
        elevation: 10.0,
        shape: RoundedRectangleBorder(
            borderRadius: getBorderRadius(index, Listado().grupos)),
        child: Padding(
          padding: const EdgeInsets.all(25.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Text(g.nombre!.value),
              Text(g.counters!.length.toString()),
            ],
          ),
        ),
      ),
    );

Widget cCardItemContador(Contador c, int index) => Obx(
      () => GestureDetector(
        onTap: () => Listado().cActual = c,
        child: Card(
          color: Temas().getSecondary(),
          shape: RoundedRectangleBorder(
              borderRadius:
                  getBorderRadius(index, Listado().gActual.counters!)),
          child: Padding(
            padding: const EdgeInsets.all(15),
            child: Row(
              children: [
                Expanded(
                  flex: 2,
                  child: Image.network(
                    c.image!.value,
                  ),
                ),
                Expanded(
                  flex: 6,
                  child: Center(
                    child: Column(
                      children: [
                        Text(
                          c.name!.value,
                          style: TextStyle(color: Temas().getTextColor()),
                        ),
                        Text(
                          c.count!.value.toString(),
                          style: TextStyle(color: Temas().getTextColor()),
                        ),
                        Row(
                          children: [
                            IconButton(
                              onPressed: () {},
                              icon: const Icon(
                                Icons.add_circle_outline_rounded,
                                color: Colors.greenAccent,
                              ),
                            ),
                            IconButton(
                              onPressed: () {},
                              icon: const Icon(
                                Icons.remove_circle_outline_rounded,
                                color: Colors.redAccent,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
                Expanded(
                  flex: 2,
                  child: IconButton(
                    onPressed: () {},
                    icon: const Icon(
                      Icons.delete_outline_rounded,
                      color: Colors.deepOrangeAccent,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );

BorderRadiusGeometry getBorderRadius(int pos, List<dynamic> lista) {
  BorderRadiusGeometry ret = BorderRadius.circular(25);

  if (lista.length != 1) {
    if (pos == 0) {
      ret = const BorderRadius.only(
          bottomLeft: Radius.circular(5),
          bottomRight: Radius.circular(5),
          topLeft: Radius.circular(25),
          topRight: Radius.circular(25));
    } else if (pos == lista.length - 1) {
      ret = const BorderRadius.only(
          bottomLeft: Radius.circular(25),
          bottomRight: Radius.circular(25),
          topLeft: Radius.circular(5),
          topRight: Radius.circular(5));
    } else {
      ret = BorderRadius.circular(5);
    }
  }

  return ret;
}
