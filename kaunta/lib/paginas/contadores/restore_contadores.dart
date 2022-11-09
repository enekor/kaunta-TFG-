import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget restoreContadores(BuildContext context) => Obx(
      () {
        if (ApiCall().conectado) {
          ApiCall().getContadores(false);
        }
        return Container(
          margin: const EdgeInsets.all(25),
          child: ListView.builder(
            itemCount: Listado().gActual.counters!.length,
            itemBuilder: (context, index) => Obx(
              () => Listado().gActual.counters![index].active!.value == false
                  ? cRestoreConterCardItem(
                      Listado().gActual.counters![index],
                      context,
                    )
                  : const SizedBox(height: 1),
            ),
          ),
        );
      },
    );
