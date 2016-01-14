DESCRIPTION = "udev rules for bohr"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://network-bohr.rules"

COMPATIBLE_MACHINE = "(bohr)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/network-bohr.rules ${D}${sysconfdir}/udev/rules.d/network.rules
}
