import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget restoreContadores(BuildContext context) => Obx(
      () {
        if (Globales().conectado) {
          ApiCall().getContadores(false);
        }

        return Container(
          margin: const EdgeInsets.all(25),
          child: ListView.builder(
            itemCount: Listado()
                .usuario
                .value
                .grupos![Listado().gActual]
                .counters!
                .length,
            itemBuilder: (context, index) => Obx(
              () => Listado()
                          .usuario
                          .value
                          .grupos![Listado().gActual]
                          .counters![index]
                          .active!
                          .value ==
                      false
                  ? cRestoreConterCardItem(
                      Listado()
                          .usuario
                          .value
                          .grupos![Listado().gActual]
                          .counters![index],
                      context,
                    )
                  : const SizedBox(height: 1),
            ),
          ),
        );
      },
    );
