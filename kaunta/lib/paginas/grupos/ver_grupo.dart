import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget verGrupos() => Obx(
      () => Container(
        margin: const EdgeInsets.all(25),
        child: Container(
          child: ListView.builder(
            itemCount: Listado().usuario.grupos!.length,
            itemBuilder: (context, index) => Obx(
              () => Listado().usuario.grupos![index].activo!.value
                  ? GestureDetector(
                      onTap: () {
                        Listado().gActual = Listado().usuario.grupos![index];
                        Listado().verGrupos.value = false;
                      },
                      child: cGroupListItem(
                        Listado().usuario.grupos![index],
                        index,
                        context,
                      ),
                    )
                  : const SizedBox(
                      height: 1,
                    ),
            ),
          ),
        ),
      ),
    );
