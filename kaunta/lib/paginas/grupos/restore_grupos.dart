import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget restoreGrupos(BuildContext context) => Obx(
      () => Container(
        margin: const EdgeInsets.all(25),
        child: ListView.builder(
          itemCount: Listado().usuario.value.grupos!.length,
          itemBuilder: (context, index) => Obx(
            () => Listado().usuario.value.grupos![index].activo!.value == false
                ? cRestoreGroupCardItem(
                    Listado().usuario.value.grupos![index],
                    context,
                  )
                : const SizedBox(height: 1),
          ),
        ),
      ),
    );
