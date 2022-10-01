class CrearContador {
  String? name;
  String? description;
  int? count;
  int? group;

  CrearContador({this.name, this.description, this.count, this.group});

  CrearContador.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    description = json['description'];
    count = json['count'];
    group = json['group'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['description'] = description;
    data['count'] = count;
    data['group'] = group;
    return data;
  }
}
