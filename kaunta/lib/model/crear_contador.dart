class CrearContador {
  String? name;
  String? description;
  String? image;
  int? count;
  int? group;

  CrearContador(
      {this.name, this.description, this.count, this.group, this.image});

  CrearContador.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    description = json['description'];
    count = json['count'];
    group = json['group'];
    image = json['image'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['description'] = description;
    data['count'] = count;
    data['group'] = group;
    data['image'] = image;
    return data;
  }
}
